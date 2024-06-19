package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.state.{GameState, SetPredictionEvent}
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.Command

class SetPredictionCommand(val controller: IController, player: Player, prediction: Int) extends Command {
  var memento: GameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(SetPredictionEvent(player, prediction))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
