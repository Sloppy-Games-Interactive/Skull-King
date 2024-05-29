package de.htwg.se.skullking.controller

import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{Observable, ObservableEvent}

class Controller(var state: GameState = GameState()) extends Observable {
  def newGame: Unit = {
    state = GameState()
    notifyObservers(ControllerEvents.NewGame)
  }
  
  def addPlayer(name: String): Unit = {
    state = state.addPlayer(Player(name))
    notifyObservers(ControllerEvents.PlayerAdded)
  }
  
  def prepareRound: Unit = {
    state = state.startNewRound
    notifyObservers(ControllerEvents.RoundPrepared)
  }
  
  def dealCards: Unit = {
    state = state.dealCards
    notifyObservers(ControllerEvents.CardsDealt)
  }
  
  def startTrick: Unit = {
    //TODO: implement
    notifyObservers(ControllerEvents.TrickStarted)
    
  }
  
  def setPrediction(player: Player, prediction: Int): Unit = {
    state = state.setPrediction(player, prediction)
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
}
