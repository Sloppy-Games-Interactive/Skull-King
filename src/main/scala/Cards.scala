trait Readable {
  def readable: String
}

enum SuitType {
  case Standard
  case Trump
}

enum Suit(val color:String, val sType:SuitType, val readable: String) extends Readable {
  case Red extends Suit("red", SuitType.Standard, "🟥")
  case Yellow extends Suit("yellow", SuitType.Standard, "🟡")
  case Blue extends Suit("blue", SuitType.Standard, "🔷")
  case Black extends Suit("black", SuitType.Trump, "☠️")
}

enum SpecialCard(val readable: String) extends Readable {
  case Joker extends SpecialCard("🤡")
  case Mermaid extends SpecialCard("🧜")
  case SkullKing extends SpecialCard("👑")
  case Pirate extends SpecialCard("🍻")
  case Escape extends SpecialCard("🚣")
}

case class Card(suit: Readable, value: Int) {
  override def toString: String = {
    s"${suit.readable} $value"
  }
}

val redCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Red, value))
val blueCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Blue, value))
val yellowCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Yellow, value))
val blackCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Black, value))
val specialCards: Vector[Card] = Vector(Card(SpecialCard.Joker, 0), Card(SpecialCard.Mermaid, 0),
  Card(SpecialCard.SkullKing, 0), Card(SpecialCard.Pirate, 0), Card(SpecialCard.Escape, 0))

val allCards: List[Card] = (redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList
