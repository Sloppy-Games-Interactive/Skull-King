package de.htwg.se.skullking.model.trick

import de.htwg.se.skullking.model.card.{Card, Suit}
import de.htwg.se.skullking.model.player.Player


class Trick(val stack: List[(Card, Player)] = List()) {
  def cards: List[Card] = stack.map((card, player) => card)
  def players: List[Player] = stack.map((card, player) => player)
  def leadSuit: Suit|Any = cards match {
    // first suit leads the trick
    case suitLead if suitLead.nonEmpty && !suitLead.head.isSpecial => suitLead.head.suit
    // escape was played first -> first suit after escape leads the trick
    case escape if escape.nonEmpty && escape.head.suit == Suit.Escape => escape.collectFirst({ case c if !c.isSpecial => c.suit }).getOrElse(None)
    // special card was played first -> no lead suit
    case _ => None
  }
  def hasSkullKing: Boolean = cards.exists(c => c.suit == Suit.SkullKing)
  def hasPirate: Boolean = cards.exists(c => c.suit == Suit.Pirate)
  def hasMermaid: Boolean = cards.exists(c => c.suit == Suit.Mermaid)
  def play(card: Card, player: Player): Trick = Trick(stack :+ (card, player))
  def winner: Option[Player] = {
      val handlers = List(winnerAllEscapeWinnerHandler(), winnerSpecialWinnerHandler(), winnerTrumpWinnerHandler(), winnerLeadSuitWinnerHandler())

      handlers.collectFirst({ case h if h.handle(this).isDefined => h.handle(this).get })
  }
  def calculateBonusPoints: Int = {
    val bonusHandlers = List(StandardBonusPointsHandler(), TrumpBonusPointsHandler(), SpecialBonusPointsHandler())

    bonusHandlers.map(h => h.handle(this)).sum
  }
}
