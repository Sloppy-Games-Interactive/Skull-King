import scala.annotation.targetName

trait Readable {
  def readable: String
  def cardType: CardType
}

enum CardType {
  case Standard
  case Trump
  case Special
}

enum Suit(val color:String, val cardType:CardType, val readable: String) extends Readable {
  case Red extends Suit("red", CardType.Standard, "🟥")
  case Yellow extends Suit("yellow", CardType.Standard, "🟡")
  case Blue extends Suit("blue", CardType.Standard, "🔷")
  case Black extends Suit("black", CardType.Trump, "☠️")
}

enum SpecialCard(val readable: String, val cardType:CardType) extends Readable {
  case Joker extends SpecialCard("🤡", CardType.Special)
  case Mermaid extends SpecialCard("🧜", CardType.Special)
  case SkullKing extends SpecialCard("👑", CardType.Special)
  case Pirate extends SpecialCard("🍻", CardType.Special)
  case Escape extends SpecialCard("🚣", CardType.Special)
}

class Card(val suit: Readable, val value: Int) {
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
  def compare(that: Card): Int = {
    if (this.isSpecial && !that.isSpecial) {
      return 1
    }

    if (!this.isSpecial && that.isSpecial) {
      return -1
    }

    if (this.isSpecial && that.isSpecial) {
      // handle both special
      // for now just pretend that first card always wins
      return 1
    }

    if (this.suit.eq(that.suit)) {
      return this.value - that.value
    }

    if (that.isTrump) {
      return -1
    }

    return 1
  }
  @targetName("lessThanComparableCard")
  def <(that: Card): Boolean = {
    this.compare(that) < 0
  }
  @targetName("greaterThanOrEqualComparableCard")
  def <=(that: Card): Boolean = {
    this.compare(that) <= 0
  }
  @targetName("greaterThanComparableCard")
  def >(that: Card): Boolean = {
    this.compare(that) > 0
  }
  @targetName("lessThanOrEqualComparableCard")
  def >=(that: Card): Boolean = {
    this.compare(that) >= 0
  }
  @targetName("equalsComparableCard")
  def ==(that: Card): Boolean = {
    this.compare(that) == 0
  }
  @targetName("notEqualsComparableCard")
  def !=(that: Card): Boolean = {
    this.compare(that) != 0
  }
}

val redCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Red, value))
val blueCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Blue, value))
val yellowCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Yellow, value))
val blackCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Black, value))
val specialCards: Vector[Card] = Vector(Card(SpecialCard.Joker, 0), Card(SpecialCard.Mermaid, 0),
  Card(SpecialCard.SkullKing, 0), Card(SpecialCard.Pirate, 0), Card(SpecialCard.Escape, 0))

val allCards: List[Card] = (redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList
