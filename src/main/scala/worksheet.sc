import scala.util.Random

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

val card = Card(10, Suit.Red)
println(card)

case class Deck(cards:List[Card]) {
  var currentCards: List[Card] = cards
  def shuffle(): Unit = {
    currentCards = Random.shuffle(currentCards)
  }
  def draw(): Card = {
    val drawnCard = currentCards.last
    currentCards = currentCards.dropRight(1)
    drawnCard
  }
}

val redCards = (1 to 14).map(Card(_, Suit.Red))
val blueCards = (1 to 14).map(Card(_, Suit.Blue))
val yellowCards = (1 to 14).map(Card(_, Suit.Yellow))
val blackCards = (1 to 14).map(Card(_, Suit.Black))
val specialCards = Vector(Card(0, SpecialCard.Joker), Card(0, SpecialCard.Mermaid), Card(0, SpecialCard.SkullKing), Card(0, SpecialCard.Pirate), Card(0, SpecialCard.Escape))


val allCards = (redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList


val deck = Deck(allCards)
deck.currentCards
deck.shuffle()
deck.currentCards
deck.draw()
deck.currentCards


case class ImmutableDeck(cards:List[Card]) {
  def shuffle(): ImmutableDeck = {
    ImmutableDeck(Random.shuffle(cards))
  }
  def draw(): (Card, ImmutableDeck) = {
    (cards.last, ImmutableDeck(cards.dropRight(1)))
  }
}

val immDeck = ImmutableDeck(allCards)
val shuffled = immDeck.shuffle()
val (drawnCard, newDeck) = shuffled.draw()

drawnCard
newDeck
newDeck.draw()
