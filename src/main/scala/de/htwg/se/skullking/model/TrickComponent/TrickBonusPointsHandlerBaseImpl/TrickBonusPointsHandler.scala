package de.htwg.se.skullking.model.TrickComponent.TrickBonusPointsHandlerBaseImpl

import de.htwg.se.skullking.model.CardComponent.{IStandardCard, Suit}
import de.htwg.se.skullking.model.TrickComponent._

trait BonusPointsHandler {
  def handle(t: ITrick): Int
}

/**
 * 10 points for each standard suit value 14 card
 */
class StandardBonusPointsHandler extends BonusPointsHandler {
  override def handle(t: ITrick): Int = t.cards.collect{
    case c: IStandardCard => c
  }.count(c => !c.isTrump && !c.isSpecial && c.value == 14) * 10
}

/**
 * 20 points for trump 14 card
 */
class TrumpBonusPointsHandler extends BonusPointsHandler {
  override def handle(t: ITrick): Int = t.cards.collect{
    case c: IStandardCard => c
  }.count(c => c.isTrump && c.value == 14) * 20
}

/**
 * <b>Special Bonus Points - only one applies:</b><br>
 * - 40 points if skull king + mermaid present<br>
 * if pirates are also present, we still only keep the mermaid bonus<br>
 * - 30 points per pirate if skull king present<br>
 * - 20 points per mermaid if at least one pirate present
 */
class SpecialBonusPointsHandler extends BonusPointsHandler {
  override def handle(t: ITrick): Int = t.cards match {
    // 40 points if skull king + mermaid present
    // if pirates are also present, we still only keep the mermaid bonus
    case mermaidBonus if t.hasSkullKing && t.hasMermaid => 40
    // 30 points per pirate if skull king present
    case withSkullKing if t.hasSkullKing => withSkullKing.count(c => c.suit == Suit.Pirate) * 30
    // 20 points per mermaid if at least one pirate present
    case withPirate if t.hasPirate => withPirate.count(c => c.suit == Suit.Mermaid) * 20
    case _ => 0
  }
}

val trickPointsHandlers = List(StandardBonusPointsHandler(), TrumpBonusPointsHandler(), SpecialBonusPointsHandler())

class TrickBonusPointsHandler extends ITrickBonusPointsHandler {
  def handle(t: ITrick): Int = trickPointsHandlers.map(_.handle(t)).sum
}
