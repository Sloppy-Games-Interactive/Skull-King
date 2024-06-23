package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.StateComponent.{IGameState, Phase}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.util.{Observable, ObservableEvent, UndoManager}

trait IController() extends Observable {
  var state: IGameState

  def handleState(): Unit

  def undo: Unit

  def redo: Unit

  def newGame: Unit

  def setPlayerLimit(limit: Int): Unit
  
  def addPlayer(name: String): Unit

  def playCard(player: IPlayer, card: ICard): Unit
  
  def setPrediction(player: IPlayer, prediction: Int): Unit
  
  def saveGame: Unit
  
  def loadGame: Unit
  
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
  case SaveGame
  case LoadGame
}
