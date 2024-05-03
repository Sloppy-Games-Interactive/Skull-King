package controller

import model.{GameState, Player}
import util.Observable

class Controller(var state: GameState = GameState()) extends Observable{
  
  def newGame: Unit = {
    state = GameState()
    notifyObservers()
  }
  
  def addPlayer(name: String): Unit = {
    state = state.addPlayer(Player(name))
    notifyObservers()
  }
  
  def prepareRound: Unit = {
    state = state.startNewRound
    notifyObservers()
  }
  
  def dealCards: Unit = {
    state = state.dealCards
    notifyObservers()
  }
  
  def startTrick: Unit = {
    //TODO: implement
    notifyObservers()
    
  }
}
