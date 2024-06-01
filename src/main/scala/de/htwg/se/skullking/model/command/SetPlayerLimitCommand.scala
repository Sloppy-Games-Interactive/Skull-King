package de.htwg.se.skullking.model.command

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.state.{GameState, SetPlayerLimitEvent}
import de.htwg.se.skullking.util.Command

class SetPlayerLimitCommand(val controller: Controller, limit: Int) extends Command {
  var memento: GameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(SetPlayerLimitEvent(limit))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
