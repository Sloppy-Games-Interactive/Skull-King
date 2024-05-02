package model

case class Card(suit: Readable, value: Int) extends Ordered[Card] {
  override def toString: String = {
    s"${suit.readable} $value"
  }

  def isSpecial: Boolean = suit match {
    case _: SpecialCard => true
    case _ => false
  }

  def isTrump: Boolean = suit match {
    case _: Suit => suit.cardType == CardType.Trump
    case _ => false
  }

  /**
   *
   * @param that the card to compare to
   * @return 1 if this card wins, -1 if that card wins, 0 if they are equal
   */
  def compare(that: Card): Int = {
    (this.suit, that.suit) match
      case (s1: SpecialCard, s2: Suit) => 1
      case (s1: Suit, s2: SpecialCard) => -1
      // TODO handle both special
      // for now just pretend that first card always wins
      case (s1: SpecialCard, s2: SpecialCard) => 1
      case (s1: Suit, s2: Suit) if (s1.color == s2.color) => this.value - that.value
      case (s1: Suit, s2: Suit) if (s2.cardType == CardType.Trump) => -1
      case _ => 1
  }
}
