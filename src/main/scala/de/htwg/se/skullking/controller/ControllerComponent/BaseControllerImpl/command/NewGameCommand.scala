package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.SkullKingModule.given
import de.htwg.se.skullking.model.StateComponent.IGameState
import de.htwg.se.skullking.util.Command

class NewGameCommand(val controller: IController) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = summon[IGameState]
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
