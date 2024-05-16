//import de.htwg.se.skullking.model._
//
//val state = GameState()
//
//// that's one way to sort cards when printing a player hand...
//state.startNewRound.deck.shuffle().cards.sortBy(_.value).groupBy(_.suit).foreach(println)


// that's one way to sort cards when printing a player hand...
//state.startNewRound.deck.shuffle().cards.sortBy(_.value).groupBy(_.suit).foreach(println)

val deck = DeckFactory("full")
deck.shuffle()
deck.length



object CardType extends Enumeration {
  type CardType = Value
  val Standard, Trump, Special = Value
}

object SuitType extends Enumeration {
  type SuitType = Value
  val Red, Yellow, Blue, Black, SkullKing, Pirate, Mermaid, Escape, Joker = Value
}

import CardType.CardType
import SuitType.SuitType

object Suit {
  def apply(suitType: SuitType): Suit = suitType match {
    case SuitType.Red => new Suit(suitType, CardType.Standard, "🟥")
    case SuitType.Yellow => new Suit(suitType, CardType.Standard, "🟡")
    case SuitType.Blue => new Suit(suitType, CardType.Standard, "🔷")
    case SuitType.Black => new Suit(suitType, CardType.Trump, "☠️")
    case SuitType.Escape => new Suit(suitType, CardType.Special, "🏝️")
    case SuitType.SkullKing => new Suit(suitType, CardType.Special, "💀")
    case SuitType.Pirate => new Suit(suitType, CardType.Special, "🏴‍☠️")
    case SuitType.Mermaid => new Suit(suitType, CardType.Special, "🧜")
    case SuitType.Joker => new Suit(suitType, CardType.Special, "🃏")
  }
}

class Suit(val suitType: SuitType, val cardType: CardType, val readable: String) {}

abstract class Card(suit: Suit) {
  def isSpecial: Boolean = suit.cardType match {
    case CardType.Special => true
    case _ => false
  }

  def isTrump: Boolean = suit.cardType match {
    case CardType.Trump => true
    case _ => false
  }
}

case class StandardCard(suit: Suit, value: Int) extends Card(suit) {
  override def toString: String = s"${suit.readable} $value"
}

class SpecialCard(val suit: Suit) extends Card(suit) {
  override def toString: String = s"${suit.readable}"
}

enum JokerBehaviour {
  case Pirate
  case Escape

  override def toString: String = this match {
    case Pirate => "🏴‍☠️"
    case Escape => "🏝️"
  }
}

trait Joker {
  val as: JokerBehaviour
  override def toString: String = s"${super.toString} ${as}"
}

case class JokerCard(as: JokerBehaviour = JokerBehaviour.Pirate) extends SpecialCard(Suit(SuitType.Joker)) with Joker
