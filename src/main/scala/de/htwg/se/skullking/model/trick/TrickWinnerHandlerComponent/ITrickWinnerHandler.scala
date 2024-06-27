package de.htwg.se.skullking.model.trick.TrickWinnerHandlerComponent

import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.trick.TrickComponent.ITrick

trait ITrickWinnerHandler {
  def handle(t: ITrick): Option[IPlayer]
}
