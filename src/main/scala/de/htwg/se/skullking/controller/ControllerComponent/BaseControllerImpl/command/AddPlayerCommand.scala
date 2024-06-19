package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.StateComponent.{AddPlayerEvent, IGameState}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
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
