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

case class Card(value:Int, suit:Readable) {
  override def toString: String = {
    s"Card: ${suit.readable} $value"
  }
}

val redCards: IndexedSeq[Card] = (1 to 14).map(Card(_, Suit.Red))
val blueCards: IndexedSeq[Card] = (1 to 14).map(Card(_, Suit.Blue))
val yellowCards: IndexedSeq[Card] = (1 to 14).map(Card(_, Suit.Yellow))
val blackCards: IndexedSeq[Card] = (1 to 14).map(Card(_, Suit.Black))
val specialCards: Vector[Card] = Vector(Card(0, SpecialCard.Joker), Card(0, SpecialCard.Mermaid), 
  Card(0, SpecialCard.SkullKing), Card(0, SpecialCard.Pirate), Card(0, SpecialCard.Escape))