package de.htwg.se.skullking.controller.command

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.util.Command

class NewGameCommand(val controller: Controller) extends Command {
  val originalState: GameState = controller.state
  var memento: GameState = controller.state
  override def doStep: Unit = {
    memento = GameState()
    controller.state = memento
  }
  override def undoStep: Unit = controller.state = originalState
  override def redoStep: Unit = controller.state = memento
}
