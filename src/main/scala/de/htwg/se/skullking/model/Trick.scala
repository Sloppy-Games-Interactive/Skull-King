package de.htwg.se.skullking.model

class Trick(val stack: List[(Card, Int)] = List()) {
  def cards: List[Card] = stack.map((c, id) => c)
  def players: List[Int] = stack.map((c, id) => id)
  def leadSuit: Suit|Any = cards match {
    // first suit leads the trick
    case suitLead if suitLead.nonEmpty && !suitLead.head.isSpecial => suitLead.head.suit
    // escape was played first -> first suit after escape leads the trick
    case escape if escape.nonEmpty && escape.head.suit == SpecialSuit.Escape => escape.collectFirst({ case c if !c.isSpecial => c.suit }).getOrElse(None)
    // special card was played first -> no lead suit
    case _ => None
  }
  def hasSkullKing: Boolean = cards.exists(c => c.suit == SpecialSuit.SkullKing)
  def hasPirate: Boolean = cards.exists(c => c.suit == SpecialSuit.Pirate)
  def hasMermaid: Boolean = cards.exists(c => c.suit == SpecialSuit.Mermaid)

  def play(card: Card, id: Int): Trick = Trick(stack :+ (card, id))
  def winner: Int = {
    stack match {
      // if all cards are escape, first played wins the trick
      case allEscape if allEscape.forall((c, id) => c.suit == SpecialSuit.Escape) => players.head
      // filter out escape cards, playing escape means losing the trick
      case withSpecial if withSpecial.filter((c, id) => c.suit != SpecialSuit.Escape).exists((c, id) => c.isSpecial) => {
        withSpecial.filter((c, id) => c.suit != SpecialSuit.Escape) match {
          /**
           * mermaid loses to pirate, if no skull king is present
           * mermaid beats all suits and skull king
           * if skull king and pirate are present, mermaid wins
           * if multiple mermaids were played, first one wins
           */
          case mermaidWin if (hasMermaid && hasSkullKing) || (hasMermaid && !hasPirate) => mermaidWin.collectFirst({ case (c, id) if c.suit == SpecialSuit.Mermaid => id }).get
          /**
           * pirate loses to skull king
           * pirate beats all suits and mermaids
           */
          case pirateWin if !hasSkullKing && hasPirate => pirateWin.collectFirst({ case (c, id) if c.suit == SpecialSuit.Pirate => id }).get
          /**
           * only skull king remains
           * skull king beats all suits and pirates
           */
          case _ => withSpecial.collectFirst({ case (c, id) if c.suit == SpecialSuit.SkullKing => id }).get
        }
      }
      case withTrump if withTrump.exists((c, id) => c.isTrump) =>
        withTrump.filter((c, id) => c.isTrump).maxBy((c, id) => c.value)._2
      case _ => {
        stack.filter((c, id) => c.suit == leadSuit).maxBy((c, id) => c.value)._2
      }
    }
  }
  def calculateBonusPoints: Int = {
    // 10 points for each standard suit value 14 card
    val standard = cards.count(c => !c.isTrump && !c.isSpecial && c.value == 14) * 10

    // 20 points for trump 14 card
    val trump = cards.count(c => c.isTrump && c.value == 14) * 20

    val special = cards match {
      // 40 points if skull king + mermaid present
      // if pirates are also present, we still only keep the mermaid bonus
      case mermaidBonus if hasSkullKing && hasMermaid => 40
      // 30 points per pirate if skull king present
      case withSkullKing if hasSkullKing => withSkullKing.count(c => c.suit == SpecialSuit.Pirate) * 30
      // 20 points per mermaid if at least one pirate present
      case withPirate if hasPirate => withPirate.count(c => c.suit == SpecialSuit.Mermaid) * 20
      case _ => 0
    }

    standard + trump + special
  }
}
