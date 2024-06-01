package de.htwg.se.skullking.model.command

import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.util.Command
import de.htwg.se.skullking.model.player.Player


class PlayCard(val controller: Controller, val player: Player, val cardIndex: Int) extends Command {
  var memento: GameState = controller.state
  override def doStep: Unit = controller.state = controller.state.playCard(player, cardIndex)
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
  

}
