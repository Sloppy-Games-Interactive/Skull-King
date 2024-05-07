package de.htwg.se.skullking.model

case class Card(suit: SuitInterface, value: Int) {
  override def toString: String = {
    s"${suit.readable} $value"
  }

  def isSpecial: Boolean = suit match {
    case _: SpecialSuit => true
    case _ => false
  }

  def isTrump: Boolean = suit match {
    case _: Suit => suit.cardType == CardType.Trump
    case _ => false
  }
}
