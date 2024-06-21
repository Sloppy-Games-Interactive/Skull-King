package de.htwg.se.skullking.model.CardComponent.CardBaseImpl

import com.google.inject.Inject
import de.htwg.se.skullking.model.CardComponent.*

abstract class Card extends ICard {
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
  suit: Suit,
  value: Int
) extends Card with IStandardCard {
  override def toString: String = s"${suit.readable} $value"
}

class SpecialCard(val suit: Suit) extends Card with ISpecialCard {
  override def toString: String = s"${suit.readable}"
}

case class JokerCard @Inject (as: JokerBehaviour = JokerBehaviour.Pirate) extends SpecialCard(Suit.Joker) with IJokerCard {
  override def toString: String = s"${super.toString} as ${as}"
  def playAs(behaviour: JokerBehaviour): JokerCard = JokerCard(behaviour)
}

class CardFactory @Inject extends ICardFactory {
  def apply(suit: Suit, value: Int): StandardCard = suit match {
    case s: Suit => StandardCard(s, value)
  }
  
  def apply(suit: Suit): SpecialCard = suit match {
    case s: Suit if s == Suit.Joker => JokerCard()
    case s: Suit => SpecialCard(s)
  }
}
