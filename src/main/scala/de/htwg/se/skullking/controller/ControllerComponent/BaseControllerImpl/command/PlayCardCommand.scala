package de.htwg.se.skullking.controller.ControllerComponent

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.StateComponent.{IGameState, PlayCardEvent}
import de.htwg.se.skullking.util.Command

class PlayCardCommand(val controller: IController, player: IPlayer, card: ICard) extends Command {
  var memento: IGameState = controller.state
  override def doStep: Unit = controller.state = controller.state.handleEvent(PlayCardEvent(player, card))
  override def undoStep: Unit = controller.state = memento
  override def redoStep: Unit = {
    memento = controller.state
    doStep
  }
}
