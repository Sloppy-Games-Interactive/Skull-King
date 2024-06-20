package de.htwg.se.skullking.model.CardComponent

trait ICard {
  val suit: Suit

  def isSpecial: Boolean

  def isTrump: Boolean
}

trait IStandardCard(val suit: Suit, val value: Int) extends ICard
trait ISpecialCard extends ICard

enum JokerBehaviour {
  case Pirate
  case Escape

  override def toString: String = this match {
    case Pirate => Suit.Pirate.readable
    case Escape => Suit.Escape.readable
  }
}

trait IJokerCard extends ISpecialCard {
  val as: JokerBehaviour
  def playAs(behaviour: JokerBehaviour): IJokerCard
}

trait ICardFactory {
  def createCard(suit: Suit, value: Int): ICard
  def createCard(suit: Suit): ICard
}
