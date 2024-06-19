package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.card.Card
import de.htwg.se.skullking.model.state.{GameState, Phase}
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{Observable, ObservableEvent, UndoManager}

trait IController() extends Observable {
  var state: GameState

  def handleState(): Unit

  def undo: Unit

  def redo: Unit

  def newGame: Unit

  def setPlayerLimit(limit: Int): Unit
  
  def addPlayer(name: String): Unit

  def playCard(player: Player, card: Card): Unit
  
  def setPrediction(player: Player, prediction: Int): Unit
  
  def quit: Unit
}

enum ControllerEvents extends ObservableEvent {
  case NewGame
  case PromptPlayerLimit
  case PromptPlayerName
  case PlayerAdded
  case PlayerLimitSet
  case PromptPrediction
  case PredictionSet
  case PromptCardPlay
  case PromptNewGame
  case Quit
  case Undo
  case Redo
  case CardPlayed
}
