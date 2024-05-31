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

class PlayerList(val players: List[Player] = List()) {
  def foldLeft[B](z: B)(op: (B, Player) => B): B = players.foldLeft(z)(op)
  def collect[B](pf: PartialFunction[Player, B]): List[B] = players.collect(pf)
  def head: Player = players.head
  def indexOf(elem: Player): Int = players.indexOf(elem)
  def lift(index: Int): Option[Player] = players.lift(index)
  def map[B](f: Player => B): List[B] = players.map(f)
  def length: Int = players.length

  def active: Option[Player] = players.find(_.active)
  def prepend(player: Player): PlayerList = PlayerList(player :: players)
  def append(player: Player): PlayerList = PlayerList(players :+ player)
  def setFirstActive: PlayerList = {
    val updatedPlayers = players.head.copy(active = true) :: players.tail.map(_.copy(active = false))
    PlayerList(updatedPlayers)
  }
  def setNextActive: PlayerList = {
    val nextPlayer = active match {
      case Some(player) =>
        val nextIndex = players.indexOf(player) + 1
        players.lift(nextIndex)
      case None => None
    }
    val updatedPlayers = players.collect {
      case p if nextPlayer.isDefined && p.name == nextPlayer.get.name => p.copy(active = true)
      case p => p.copy(active = false)
    }
    PlayerList(updatedPlayers)
  }
  def resetHands: PlayerList = PlayerList(players.map(_.resetHand))
  def resetPredictions: PlayerList = PlayerList(players.map(_.resetPrediction))
  def resetActive: PlayerList = PlayerList(players.map(_.copy(active = false)))
  def updatePlayer(player: Player): PlayerList = {
    val updatedPlayers = players.collect {
      case p if p.name == player.name => player
      case p => p
    }
    PlayerList(updatedPlayers)
  }

  override def toString: String = players.mkString("\n")
}

case class GameState(
  phase: Phase = Phase.PrepareGame,
  playerLimit: Int = 0,
  players: PlayerList = PlayerList(),
  round: Int = 0,
  tricks: List[Trick] = List(),
  deck: Deck = Deck(),
) {
  private def changePhase(nextPhase: Phase): GameState = this.copy(phase = nextPhase)

  def activePlayer: Option[Player] = players.active

  def setPlayerLimit(n: Int): GameState = this.copy(playerLimit = n)

  def addPlayer(player: Player): GameState = {
    val nextState = this.copy(players = players.append(player))
    if (players.length < playerLimit - 1) {
      nextState
    } else {
      nextState.prepareRound
    }
  }

  private def prepareRound: GameState = {
    val updatedPlayers = players.setFirstActive.resetHands.resetPredictions

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

    this.copy(players = PlayerList(updatedPlayers.reverse), deck = newDeck)
  }

  def setPrediction(player: Player, newPrediction: Int): GameState = {
    val updatedPlayers = PlayerList(players.collect {
      case p if p.name == player.name => p.copy(prediction = Some(newPrediction))
      case p => p.copy(active = false)
    }).setNextActive

    val nextState = this.copy(players = updatedPlayers)
    if (players.setNextActive.active.isEmpty) {
      nextState.startTrick
    } else {
      nextState
    }
  }

  private def startTrick: GameState = {
    this.copy(
      tricks = Trick() :: tricks,
      players = players.setFirstActive
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
      players = PlayerList(updatedPlayers),
      tricks = List()
    )

    if (round == 10) {
      nextState.changePhase(Phase.EndGame)
    } else {
      nextState.prepareRound
    }
  }
}
