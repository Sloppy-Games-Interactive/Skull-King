package de.htwg.se.skullking.model.deck

import de.htwg.se.skullking.model.card.Card

import scala.util.Random

class Deck(cards: List[Card] = List()) {
  /**
   * shuffle cards in the card list
   *
   * @return mixed card list
   */
  def shuffle(): Deck = {
    Deck(Random.shuffle(cards))
  }

  def length: Int = cards.length
  def getCards: List[Card] = cards
  /**
   *
   * @param n is the number of cards to draw
   * @return the drawn cards and the remaining deck
   */
  def draw(n: Int = 1): (List[Card], Deck) = {
    (cards.takeRight(n), Deck(cards.dropRight(n)))
  }

  override def toString: String = {
    s"[ ${cards.mkString(", ")} ]"
  }
}
