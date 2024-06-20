package de.htwg.se.skullking.model.CardComponent

abstract class Card(val suit: Suit) extends ICard {
  def isSpecial: Boolean = suit.cardType match {
    case CardType.Special => true
    case _ => false
  }

  def isTrump: Boolean = suit.cardType match {
    case CardType.Trump => true
    case _ => false
  }
}

case class StandardCard(
  override val suit: Suit,
  override val value: Int
) extends Card(suit) with IStandardCard(suit, value) {
  override def toString: String = s"${suit.readable} $value"
}

class SpecialCard(override val suit: Suit) extends Card(suit) with ISpecialCard {
  override def toString: String = s"${suit.readable}"
}

case class JokerCard(as: JokerBehaviour = JokerBehaviour.Pirate) extends SpecialCard(Suit.Joker) with IJokerCard {
  override def toString: String = s"${super.toString} as ${as}"
  def playAs(behaviour: JokerBehaviour): JokerCard = JokerCard(behaviour)
}

object CardFactory extends ICardFactory {
  def createCard(suit: Suit, value: Int): StandardCard = suit match {
    case s: Suit => StandardCard(s, value)
  }
  
  def createCard(suit: Suit): SpecialCard = suit match {
    case s: Suit if s == Suit.Joker => JokerCard()
    case s: Suit => SpecialCard(s)
  }
}
