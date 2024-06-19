package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.card.Card
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.model.state.{GameState, PlayCardEvent}
import de.htwg.se.skullking.util.Command

class PlayCardCommand(val controller: IController, player: Player, card: Card) extends Command {
  var memento: GameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(PlayCardEvent(player, card))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
