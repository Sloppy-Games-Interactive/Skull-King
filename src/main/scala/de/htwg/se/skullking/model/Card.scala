package de.htwg.se.skullking.model

abstract class Card(val suit: Suit) {
  def isSpecial: Boolean = suit.cardType match {
    case CardType.Special => true
    case _ => false
  }

  def isTrump: Boolean = suit.cardType match {
    case CardType.Trump => true
    case _ => false
  }
}

case class StandardCard(override val suit: Suit, value: Int) extends Card(suit) {
  override def toString: String = s"${suit.readable} $value"
}

class SpecialCard(override val suit: Suit) extends Card(suit) {
  override def toString: String = s"${suit.readable}"
}

enum JokerBehaviour {
  case Pirate
  case Escape

  override def toString: String = this match {
    case Pirate => Suit.Pirate.readable
    case Escape => Suit.Escape.readable
  }
}

trait Joker {
  val as: JokerBehaviour
  override def toString: String = s"${super.toString} as ${as}"
  def playAs(behaviour: JokerBehaviour): JokerCard = JokerCard(behaviour)
}

case class JokerCard(as: JokerBehaviour = JokerBehaviour.Pirate) extends SpecialCard(Suit.Joker) with Joker

object Card {
  def apply(suit: Suit, value: Int): StandardCard = suit match {
    case s: Suit => StandardCard(s, value)
  
  }
  def apply(suit: Suit): SpecialCard = suit match {
    case s: Suit if s == Suit.Joker => JokerCard()
    case s: Suit => SpecialCard(s)
  }
}
