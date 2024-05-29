package de.htwg.se.skullking.controller

import de.htwg.se.skullking.controller.command.{AddPlayerCommand, DealCardsCommand, NewGameCommand, PrepareRoundCommand, SetPredictionCommand}
import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{Observable, ObservableEvent, UndoManager}

class Controller(var state: GameState = GameState()) extends Observable {
  private val undoManager = new UndoManager

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers(ControllerEvents.Undo)
  }
  def redo: Unit = {
    undoManager.redoStep
    notifyObservers(ControllerEvents.Redo)
  }

  def newGame: Unit = {
    undoManager.doStep(new NewGameCommand(this))
    notifyObservers(ControllerEvents.NewGame)
  }
  
  def addPlayer(name: String): Unit = {
    undoManager.doStep(new AddPlayerCommand(this, Player(name)))
    notifyObservers(ControllerEvents.PlayerAdded)
  }
  
  def prepareRound: Unit = {
    undoManager.doStep(new PrepareRoundCommand(this))
    notifyObservers(ControllerEvents.RoundPrepared)
  }
  
  def dealCards: Unit = {
    undoManager.doStep(new DealCardsCommand(this))
    notifyObservers(ControllerEvents.CardsDealt)
  }
  
  def startTrick: Unit = {
    //TODO: implement
    notifyObservers(ControllerEvents.TrickStarted)
    
  }
  
  def setPrediction(player: Player, prediction: Int): Unit = {
    undoManager.doStep(new SetPredictionCommand(this, player, prediction))
    notifyObservers(ControllerEvents.PredictionSet)
  }
  
  def quit: Unit = {
    // TODO: implement save game state to file
    notifyObservers(ControllerEvents.Quit)
  }
}

enum ControllerEvents extends ObservableEvent {
  case NewGame
  case PlayerAdded
  case RoundPrepared
  case CardsDealt
  case TrickStarted
  case PredictionSet
  case Quit
  case Undo
  case Redo
}
