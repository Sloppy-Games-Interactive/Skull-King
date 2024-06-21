package de.htwg.se.skullking.model.trick.TrickBonusPointsHandlerComponent

import de.htwg.se.skullking.model.trick.TrickComponent.ITrick

trait ITrickBonusPointsHandler {
  def handle(t: ITrick): Int
}
