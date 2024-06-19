package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.state.{AddPlayerEvent, GameState}
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.Command

class AddPlayerCommand(val controller: IController, player: Player) extends Command {
  var memento: GameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(AddPlayerEvent(player))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
