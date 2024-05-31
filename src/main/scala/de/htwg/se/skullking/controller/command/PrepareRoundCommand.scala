package de.htwg.se.skullking.controller.command

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.util.Command

class PrepareRoundCommand(val controller: Controller) extends Command {
  var memento: GameState = controller.state
  override def doStep: Unit = controller.state = controller.state.startNewRound
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = doStep
}