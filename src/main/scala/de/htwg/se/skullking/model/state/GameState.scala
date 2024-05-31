package de.htwg.se.skullking.model.state

import de.htwg.se.skullking.model.deck.{Deck, DeckContent, DeckFactory}
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.model.trick.Trick

enum Phase {
  case PrepareGame
  case PrepareTricks
  case PlayTricks
  case EndGame
}

case class GameState(
  phase: Phase = Phase.PrepareGame,
  playerLimit: Int = 0,
  players: List[Player] = List(),
  round: Int = 0,
  tricks: List[Trick] = List(),
  deck: Deck = Deck(),
) {
  private def changePhase(nextPhase: Phase): GameState = this.copy(phase = nextPhase)

  def activePlayer: Option[Player] = players.find(_.active)

  def setPlayerLimit(n: Int): GameState = this.copy(playerLimit = n)

  def addPlayer(player: Player): GameState = {
    val nextState = this.copy(players = players :+ player)
    if (players.length < playerLimit - 1) {
      nextState
    } else {
      nextState.prepareRound
    }
  }

  private def prepareRound: GameState = {
    val updatedPlayers = setFirstActive(players).map(_.resetHand).map(_.resetPrediction)

    this.copy(
        round = round + 1,
        deck = DeckFactory(DeckContent.full).shuffle(),
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

  def setPrediction(player: Player, newPrediction: Int): GameState = {
    val updatedPlayers = setNextActive(players.map {
      case p if p.name == player.name => p.copy(prediction = Some(newPrediction), active = true)
      case p => p.copy(active = false)
    })

    val nextState = this.copy(players = updatedPlayers)
    if (nextActive(updatedPlayers).isEmpty) {
      nextState.startTrick
    } else {
      nextState
    }
  }

  private def startTrick: GameState = {
    this.copy(
      tricks = Trick() :: tricks,
      players = setFirstActive(players)
    ).changePhase(Phase.PlayTricks)
  }

  def playCard(cardIdx: Int): GameState = {
    this
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
      val wonTricks = tricks.collect {
        case trick if trick.winner.contains(player) => trick
      }

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

    if (round == 10) {
      nextState.changePhase(Phase.EndGame)
    } else {
      nextState.prepareRound
    }
  }

  private def setFirstActive(players: List[Player]): List[Player] = {
    players.head.copy(active = true) :: players.tail.map(_.copy(active = false))
  }

  private def setNextActive(players: List[Player]): List[Player] = {
    val nextPlayer = activePlayer match {
      case Some(player) =>
        val nextIndex = players.indexOf(player) + 1
        players.lift(nextIndex)
      case None => None
    }
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