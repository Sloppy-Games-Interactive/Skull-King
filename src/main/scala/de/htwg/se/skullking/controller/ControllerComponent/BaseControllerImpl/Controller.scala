package de.htwg.se.skullking.controller.ControllerComponent.BaseControllerImpl

import de.htwg.se.skullking.controller.ControllerComponent.*
import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.{IPlayer, IPlayerFactory}
import de.htwg.se.skullking.model.StateComponent.{IGameState, Phase}
import de.htwg.se.skullking.modules.Default.given
import de.htwg.se.skullking.util.UndoManager

class Controller(using var state: IGameState) extends IController {
  val undoManager = UndoManager()

  def handleState(): Unit = {
    state.phase match {
      case Phase.PrepareGame if state.playerLimit == 0 => notifyObservers(ControllerEvents.PromptPlayerLimit)
      case Phase.PrepareGame => notifyObservers(ControllerEvents.PromptPlayerName)
      case Phase.PrepareTricks => notifyObservers(ControllerEvents.PromptPrediction)
      case Phase.PlayTricks => notifyObservers(ControllerEvents.PromptCardPlay)
      case Phase.EndGame => notifyObservers(ControllerEvents.PromptNewGame)
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
    undoManager.doStep(new AddPlayerCommand(this, summon[IPlayerFactory].create(name)))
    notifyObservers(ControllerEvents.PlayerAdded)
    handleState()
  }

  def playCard(player: IPlayer, card: ICard): Unit = {
    undoManager.doStep(new PlayCardCommand(this, player, card))
    notifyObservers(ControllerEvents.CardPlayed)
    handleState()
  }

  def setPrediction(player: IPlayer, prediction: Int): Unit = {
    undoManager.doStep(new SetPredictionCommand(this, player, prediction))
    notifyObservers(ControllerEvents.PredictionSet)
    handleState()
  }

  def saveGame: Unit = {
    summon[IFileIO].save(state)
    notifyObservers(ControllerEvents.SaveGame)
    handleState()
  }
  
  def loadGame: Unit = {
    state = summon[IFileIO].load
    undoManager.doStep(new LoadGameCommand(this, state))
    notifyObservers(ControllerEvents.LoadGame)
    handleState()
  }
  
  def quit: Unit = {
    // TODO: implement save game state to file
    notifyObservers(ControllerEvents.Quit)
  }
}
