package controller

import model.{Player, GameState}

class Controller {
  def newGame: GameState = GameState()
  
  def addPlayer(state: GameState, name: String): GameState = state.addPlayer(Player(name))
  
  def prepareRound(state: GameState): GameState = state.startNewRound
  
  def dealCards(state: GameState): GameState = state.dealCards
  
  def startTrick(state: GameState): GameState = state
}
