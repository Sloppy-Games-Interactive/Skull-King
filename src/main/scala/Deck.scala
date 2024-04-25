import scala.util.Random

/**
 * 
 * @param cards 
 */
case class Deck(cards:List[Card]) {
  /**
   * shuffle cards in the card list
   * @return mixed card list
   */
  def shuffle(): Deck = {
    Deck(Random.shuffle(cards))
  }

  /**
   * 
   * @return
   */
  def draw(): (Card, Deck) = {
    (cards.last, Deck(cards.dropRight(1)))
  }

  /**
   * 
   * @param n
   * @return
   */
  def draw(n: Int): (List[Card], Deck) = {
    (cards.takeRight(n), Deck(cards.dropRight(n)))
  }
  override def toString: String = {
    s"[ ${cards.mkString(", ")} ]"
  }
}

val fullDeck: Deck = Deck(allCards)
