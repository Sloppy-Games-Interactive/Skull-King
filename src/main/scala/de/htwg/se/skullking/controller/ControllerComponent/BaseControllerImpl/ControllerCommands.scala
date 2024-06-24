package de.htwg.se.skullking.controller.ControllerComponent.BaseControllerImpl

import de.htwg.se.skullking.modules.Default.given
import de.htwg.se.skullking.controller.ControllerComponent.*
import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.StateComponent.{AddPlayerEvent, IGameState, PlayCardEvent, SetPlayerLimitEvent, SetPredictionEvent}
import de.htwg.se.skullking.util.Command

class AddPlayerCommand(val controller: IController, player: IPlayer) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(AddPlayerEvent(player))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}

class NewGameCommand(val controller: IController) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = summon[IGameState]
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}

class LoadGameCommand(val controller: IController, val gameState: IGameState) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = gameState
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}

class PlayCardCommand(val controller: IController, player: IPlayer, card: ICard) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(PlayCardEvent(player, card))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}

class SetPlayerLimitCommand(val controller: IController, limit: Int) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(SetPlayerLimitEvent(limit))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}

class SetPredictionCommand(val controller: IController, player: IPlayer, prediction: Int) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(SetPredictionEvent(player, prediction))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
