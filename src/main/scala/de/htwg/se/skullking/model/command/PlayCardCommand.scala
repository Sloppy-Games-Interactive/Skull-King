package de.htwg.se.skullking.model.command

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.model.state.{GameState, PlayCardEvent}
import de.htwg.se.skullking.util.Command

class PlayCardCommand(val controller: Controller, player: Player, cardIdx: Int) extends Command {
  var memento: GameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(PlayCardEvent(player, cardIdx))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
