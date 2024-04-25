package model

import scala.util.Random

/**
 *
 * @param cards is the list of cards in the deck
 */
case class Deck(cards: List[Card]) {
  /**
   * shuffle cards in the card list
   *
   * @return mixed card list
   */
  def shuffle(): Deck = {
    Deck(Random.shuffle(cards))
  }

  /**
   * Draw the top card from the deck
   * @return the top card and the remaining deck
   */
  def draw(): (Card, Deck) = {
    (cards.last, Deck(cards.dropRight(1)))
  }

  /**
   *
   * @param n is the number of cards to draw
   * @return the drawn cards and the remaining deck
   */
  def draw(n: Int): (List[Card], Deck) = {
    (cards.takeRight(n), Deck(cards.dropRight(n)))
  }

  override def toString: String = {
    s"[ ${cards.mkString(", ")} ]"
  }
}
