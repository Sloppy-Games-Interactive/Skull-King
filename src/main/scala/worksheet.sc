enum SuitType {
  case Standard
  case Trump
}

enum Suit(val color:String, val sType:SuitType, val readable: String) {
  case Red extends Suit("red", SuitType.Standard, "🟥")
  case Yellow extends Suit("yellow", SuitType.Standard, "🟡")
  case Blue extends Suit("blue", SuitType.Standard, "🔷")
  case Black extends Suit("black", SuitType.Trump, "☠️")
}

enum SpecialCard(val readable: String) {
  case Joker extends SpecialCard("🤡")
  case Mermaid extends SpecialCard("🧜")
  case SkullKing extends SpecialCard("👑")
  case Pirate extends SpecialCard("🍻")
  case Escape extends SpecialCard("🚣")
}

case class Card(value:Int, suit:Suit) {
  override def toString: String = {
    s"Card: ${suit.readable} $value"
  }
}

val card = Card(10, Suit.Red)
println(card)

case class Deck(cards:List[Card])

val redCards = (1 to 14).map(Card(_, Suit.Red))
val blueCards = (1 to 14).map(Card(_, Suit.Blue))
val yellowCards = (1 to 14).map(Card(_, Suit.Yellow))
val blackCards = (1 to 14).map(Card(_, Suit.Black))

val deck = Deck((redCards ++ blueCards ++ yellowCards ++ blackCards).toList)
