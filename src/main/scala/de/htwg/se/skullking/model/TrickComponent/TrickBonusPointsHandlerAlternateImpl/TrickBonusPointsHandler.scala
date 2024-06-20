package de.htwg.se.skullking.model.TrickComponent.TrickBonusPointsHandlerAlternateImpl

import de.htwg.se.skullking.model.TrickComponent._

trait BonusPointsHandler {
  def handle(t: ITrick): Int
}

/**
 * playing trump cars? good for you
 */
class TrumpCardsHandler extends BonusPointsHandler {
  override def handle(t: ITrick): Int = t.cards.count(c => c.isTrump) * 999
}

/**
 * -10 points for each special card. What are you? A noob?
 */
class SpecialCardsHandler extends BonusPointsHandler {
  override def handle(t: ITrick): Int = t.cards.count(c => c.isSpecial) * (-1000)
}

val trickPointsHandlers = List(TrumpCardsHandler(), SpecialCardsHandler())

class TrickBonusPointsHandler extends ITrickBonusPointsHandler {
  def handle(t: ITrick): Int = trickPointsHandlers.map(_.handle(t)).sum
}
