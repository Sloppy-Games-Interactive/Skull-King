package de.htwg.se.skullking.model

class Trick(val stack: List[(Card, Int)] = List()) {
  def cards: List[Card] = stack.map((c, id) => c)
  def players: List[Int] = stack.map((c, id) => id)
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
  def play(card: Card, id: Int): Trick = Trick(stack :+ (card, id))
  def winner: Int = {
      val handlers = List(winnerAllEscapeWinnerHandler(), winnerSpecialWinnerHandler(), winnerTrumpWinnerHandler(), winnerLeadSuitWinnerHandler())

      handlers.collectFirst({ case h if h.handle(this).isDefined => h.handle(this).get }).getOrElse(-1)
  }
  def calculateBonusPoints: Int = {
    val bonusHandlers = List(StandardBonusPointsHandler(), TrumpBonusPointsHandler(), SpecialBonusPointsHandler())

    bonusHandlers.map(h => h.handle(this)).sum
  }
}
