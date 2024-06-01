package de.htwg.se.skullking.controller

import de.htwg.se.skullking.model.state.{GameState, Phase}
import de.htwg.se.skullking.model.command.*
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{Observable, ObservableEvent, UndoManager}

class Controller(var state: GameState = GameState()) extends Observable {
  private val undoManager = new UndoManager

  def handleState(): Unit = {
    state.phase match {
      case Phase.PrepareGame if state.playerLimit == 0 => notifyObservers(ControllerEvents.PromptPlayerLimit)
      case Phase.PrepareGame => notifyObservers(ControllerEvents.PromptPlayerName)
      case Phase.PrepareTricks => notifyObservers(ControllerEvents.PromptPrediction)
      case Phase.PlayTricks =>  notifyObservers(ControllerEvents.PromptCardPlay)
      case Phase.EndGame =>  notifyObservers(ControllerEvents.PromptNewGame)
    }
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers(ControllerEvents.Undo)
    handleState()
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers(ControllerEvents.Redo)
    handleState()
  }

  def newGame: Unit = {
    undoManager.doStep(new NewGameCommand(this))
    notifyObservers(ControllerEvents.NewGame)
    handleState()
  }

  def setPlayerLimit(limit: Int): Unit = {
    undoManager.doStep(new SetPlayerLimitCommand(this, limit))
    notifyObservers(ControllerEvents.PlayerLimitSet)
    handleState()
  }
  
  def addPlayer(name: String): Unit = {
    undoManager.doStep(new AddPlayerCommand(this, Player(name)))
    notifyObservers(ControllerEvents.PlayerAdded)
    handleState()
  }

  def playCard(player: Player, cardIndex: Int): Unit = {
    undoManager.doStep(new PlayCardCommand(this, player, cardIndex))
    notifyObservers(ControllerEvents.CardPlayed)
    handleState()
  }
  
  def setPrediction(player: Player, prediction: Int): Unit = {
    undoManager.doStep(new SetPredictionCommand(this, player, prediction))
    notifyObservers(ControllerEvents.PredictionSet)
    handleState()
  }
  
  def quit: Unit = {
    // TODO: implement save game state to file
    notifyObservers(ControllerEvents.Quit)
  }
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
