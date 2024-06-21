package de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl

import com.google.inject.Inject
import com.google.inject.name.Named
//import de.htwg.se.skullking.modules.Default.given

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.DeckComponent.{DeckContent, IDeck, IDeckFactory}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.StateComponent.*
import de.htwg.se.skullking.model.TrickComponent.ITrick

class GameState @Inject(defaultDeck: IDeck, deckFactory: IDeckFactory, defaultTrick: ITrick) (
  val phase: Phase = Phase.PrepareGame,
  val playerLimit: Int = 0,
  val players: List[IPlayer] = List(),
  val round: Int = 0,
  val tricks: List[ITrick] = List(),
  val deck: IDeck = defaultDeck,
  @Named("roundLimit") val roundLimit: Int = 10
) extends IGameState {
  def copy(
    phase: Phase = phase,
    playerLimit: Int = playerLimit,
    players: List[IPlayer] = players,
    round: Int = round,
    tricks: List[ITrick] = tricks,
    deck: IDeck = deck,
    roundLimit: Int = roundLimit
  ): GameState = GameState(defaultDeck, deckFactory, defaultTrick)(
    phase,
    playerLimit,
    players,
    round,
    tricks,
    deck,
    roundLimit
  )
  
  def handleEvent(event: GameStateEvent): IGameState = event match {
    case SetPlayerLimitEvent(n) if phase == Phase.PrepareGame && players.isEmpty => setPlayerLimit(n)
    case AddPlayerEvent(player) if phase == Phase.PrepareGame && playerLimit > 0 => addPlayer(player)
    case SetPredictionEvent(player, prediction) if phase == Phase.PrepareTricks => setPrediction(player, prediction)
    case PlayCardEvent(player, card) if phase == Phase.PlayTricks => playCard(player, card)
    case _ => this
  }

  def activePlayer: Option[IPlayer] = players.find(_.active)

  def activeTrick: Option[ITrick] = tricks.headOption

  private def changePhase(nextPhase: Phase): GameState = copy(phase = nextPhase)

  private def setPlayerLimit(n: Int): GameState = copy(playerLimit = n)

  private def addPlayer(player: IPlayer): IGameState = {
    val nextState = copy(players = players :+ player)
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
        deck = deckFactory(DeckContent.specials).shuffle(),
        players = updatedPlayers
      ).dealCards
      .changePhase(Phase.PrepareTricks)
  }

  private def dealCards: GameState = {
    val (newDeck, updatedPlayers) = players.foldLeft((deck, List[IPlayer]())) {
      case ((currentDeck, playerList), player) =>
        val (newDeck, newHand) = player.hand.drawFromDeck(currentDeck, round)
        (newDeck, player.setHand(newHand) :: playerList)
    }

    this.copy(players = updatedPlayers.reverse, deck = newDeck)
  }

  private def setPrediction(player: IPlayer, newPrediction: Int): GameState = {
    val updatedPlayers = players.map {
      case p if p.name == player.name => p.setPrediction(newPrediction)
      case p => p
    }

    nextActive(updatedPlayers) match {
      case Some(nextPlayer) => this.copy(players = setNextActive(updatedPlayers))
      case None => this.copy(players = updatedPlayers).startTrick
    }
  }

  private def startTrick: GameState = {
    this.copy(
      tricks = defaultTrick :: tricks,
      players = setFirstActive(players)
    ).changePhase(Phase.PlayTricks)
  }

  private def playCard(player: IPlayer, card: ICard): GameState = {
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
        player.setScore(player.score + scoreChange)
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

  private def setFirstActive(players: List[IPlayer]): List[IPlayer] = {
    players.head.setActive(true) :: players.tail.map(_.setActive(false))
  }

  private def setNextActive(players: List[IPlayer]): List[IPlayer] = {
    val nextPlayer = nextActive(players)
    players.map {
      case p if nextPlayer.isDefined && p.name == nextPlayer.get.name => p.setActive(true)
      case p => p.setActive(false)
    }
  }

  private def nextActive(players: List[IPlayer]): Option[IPlayer] = {
    val nextIndex = players.indexWhere(_.active) + 1
    players.lift(nextIndex)
  }
}
