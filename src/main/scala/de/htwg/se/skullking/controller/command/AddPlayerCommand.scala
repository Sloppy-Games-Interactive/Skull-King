package de.htwg.se.skullking.controller.command

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.Command

class AddPlayerCommand(val controller: Controller, player: Player) extends Command {
  val originalState: GameState = controller.state
  var memento: GameState = controller.state
  override def doStep: Unit = {
    memento = controller.state.addPlayer(player)
    controller.state = memento
  }
  override def undoStep: Unit = controller.state = originalState
  override def redoStep: Unit = controller.state = memento
}
