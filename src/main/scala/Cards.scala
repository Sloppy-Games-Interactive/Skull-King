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
   * @param that
   * @return
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

// create all playable cards as map
val redCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Red, value))
val blueCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Blue, value))
val yellowCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Yellow, value))
val blackCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Black, value))
val specialCards: Vector[Card] = Vector(Card(SpecialCard.Joker, 0), Card(SpecialCard.Mermaid, 0),
  Card(SpecialCard.SkullKing, 0), Card(SpecialCard.Pirate, 0), Card(SpecialCard.Escape, 0))

// add all cards to one list
val allCards: List[Card] = (redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList
