import scala.util.Random

case class Deck(cards:List[Card]) {
  def shuffle(): Deck = {
    Deck(Random.shuffle(cards))
  }
  def draw(): (Card, Deck) = {
    (cards.last, Deck(cards.dropRight(1)))
  }
  override def toString: String = {
    s"[ ${cards.mkString(", ")} ]"
  }
}

val deck: Deck = Deck(allCards)
