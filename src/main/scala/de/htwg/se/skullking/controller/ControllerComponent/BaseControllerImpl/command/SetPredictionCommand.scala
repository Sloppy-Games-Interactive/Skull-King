package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.StateComponent.{IGameState, SetPredictionEvent}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.util.Command

class SetPredictionCommand(val controller: IController, player: IPlayer, prediction: Int) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(SetPredictionEvent(player, prediction))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
