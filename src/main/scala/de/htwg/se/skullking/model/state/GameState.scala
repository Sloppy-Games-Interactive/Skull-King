package de.htwg.se.skullking.model.state

import de.htwg.se.skullking.model.card.Card
import de.htwg.se.skullking.model.deck.{Deck, DeckContent, DeckFactory}
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.model.trick.Trick

enum Phase {
  case PrepareGame
  case PrepareTricks
  case PlayTricks
  case EndGame
}

trait GameStateEvent()
case class SetPlayerLimitEvent(n: Int) extends GameStateEvent
case class AddPlayerEvent(player: Player) extends GameStateEvent
case class SetPredictionEvent(player: Player, prediction: Int) extends GameStateEvent
case class PlayCardEvent(player: Player, card: Card) extends GameStateEvent

case class GameState(
  phase: Phase = Phase.PrepareGame,
  playerLimit: Int = 0,
  players: List[Player] = List(),
  round: Int = 0,
  tricks: List[Trick] = List(),
  deck: Deck = Deck(),
  roundLimit: Int = 10
) {
  def handleEvent(event: GameStateEvent): GameState = event match {
    case SetPlayerLimitEvent(n) if phase == Phase.PrepareGame && players.isEmpty => setPlayerLimit(n)
    case AddPlayerEvent(player) if phase == Phase.PrepareGame && playerLimit > 0 => addPlayer(player)
    case SetPredictionEvent(player, prediction) if phase == Phase.PrepareTricks => setPrediction(player, prediction)
    case PlayCardEvent(player, card) if phase == Phase.PlayTricks => playCard(player, card)
    case _ => this
  }

  def activePlayer: Option[Player] = players.find(_.active)
  def activeTrick: Option[Trick] = tricks.headOption

  private def changePhase(nextPhase: Phase): GameState = this.copy(phase = nextPhase)

  private def setPlayerLimit(n: Int): GameState = this.copy(playerLimit = n)

  private def addPlayer(player: Player): GameState = {
    val nextState = this.copy(players = players :+ player)
    if (players.length < playerLimit - 1) {
      nextState
    } else {
      nextState.prepareRound
    }
  }

  private def prepareRound: GameState = {
    val updatedPlayers = setFirstActive(players).map(_.resetHand.resetPrediction)

    this.copy(
        round = round + 1,
        deck = DeckFactory(DeckContent.specials).shuffle(), // TODO use full deck once playing joker is fully implemented
        players = updatedPlayers
      ).dealCards
      .changePhase(Phase.PrepareTricks)
  }

  private def dealCards: GameState = {
    val (newDeck, updatedPlayers) = players.foldLeft((deck, List[Player]())) {
      case ((currentDeck, playerList), player) =>
        val (newDeck, newHand) = player.hand.drawFromDeck(currentDeck, round)
        (newDeck, player.copy(hand = newHand) :: playerList)
    }

    this.copy(players = updatedPlayers.reverse, deck = newDeck)
  }

  private def setPrediction(player: Player, newPrediction: Int): GameState = {
    val updatedPlayers = players.map {
      case p if p.name == player.name => p.copy(prediction = Some(newPrediction))
      case p => p
    }

    nextActive(updatedPlayers) match {
      case Some(nextPlayer) => this.copy(players = setNextActive(updatedPlayers))
      case None => this.copy(players = updatedPlayers).startTrick
    }
  }

  private def startTrick: GameState = {
    this.copy(
      tricks = Trick() :: tricks,
      players = setFirstActive(players)
    ).changePhase(Phase.PlayTricks)
  }

  private def playCard(player: Player, card: Card): GameState = {
    val nextState = {
      for {
        _ <- players.find(_ == player)
        trick <- tricks.headOption
      } yield {
        val (playedCard, updatedPlayer) = player.playCard(card)
        val updatedTrick = trick.play(playedCard, updatedPlayer)
        val updatedPlayers = players.map {
          case p if p.name == updatedPlayer.name => updatedPlayer
          case p => p
        }

        this.copy(
          tricks = tricks.updated(0, updatedTrick),
          players = setNextActive(updatedPlayers)
        )
      }
    }.getOrElse(this)

    nextState.activeTrick match {
      case Some(trick) if trick.players.length == players.length => nextState.endTrick
      case _ => nextState
    }
  }

  private def endTrick: GameState = {
    if (tricks.length == round) {
      endRound
    } else {
      startTrick
    }
  }

  private def endRound: GameState = {
    val updatedPlayers = players.map { player =>
      val wonTricks = for {
        trick <- tricks
        winner <- trick.winner if winner.name == player.name
      } yield trick

      val numTricksWon = wonTricks.length

      player.prediction.fold(player) { prediction =>
        val scoreChange = prediction match {
          case 0 if numTricksWon == 0 => 10 * round
          case 0 => -10 * round
          case _ if prediction == numTricksWon => 20 * numTricksWon + wonTricks.map(_.calculateBonusPoints).sum
          case _ => -10 * Math.abs(numTricksWon - prediction)
        }
        player.copy(score = player.score + scoreChange)
      }
    }

    val nextState = this.copy(
      players = updatedPlayers,
      tricks = List()
    )

    if (round == roundLimit) {
      nextState.changePhase(Phase.EndGame)
    } else {
      nextState.prepareRound
    }
  }

  private def setFirstActive(players: List[Player]): List[Player] = {
    players.head.copy(active = true) :: players.tail.map(_.copy(active = false))
  }

  private def setNextActive(players: List[Player]): List[Player] = {
    val nextPlayer = nextActive(players)
    players.map {
      case p if nextPlayer.isDefined && p.name == nextPlayer.get.name => p.copy(active = true)
      case p => p.copy(active = false)
    }
  }

  private def nextActive(players: List[Player]): Option[Player] = {
    val nextIndex = players.indexWhere(_.active) + 1
    players.lift(nextIndex)
  }
}