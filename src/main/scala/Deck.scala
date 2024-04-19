import scala.util.Random

case class Deck(cards:List[Card]) {
  def shuffle(): Deck = {
    Deck(Random.shuffle(cards))
  }
  def draw(): (Card, Deck) = {
    (cards.last, Deck(cards.dropRight(1)))
  }
  def draw(n: Int): (List[Card], Deck) = {
    (cards.takeRight(n), Deck(cards.dropRight(n)))
  }
  override def toString: String = {
    s"[ ${cards.mkString(", ")} ]"
  }
}

val fullDeck: Deck = Deck(allCards)
