package de.htwg.se.skullking.model.DeckComponent

import de.htwg.se.skullking.model.CardComponent.ICard

import scala.util.Random

class Deck(cards: List[ICard] = List()) extends IDeck {
  /**
   * shuffle cards in the card list
   *
   * @return mixed card list
   */
  def shuffle(): IDeck = {
    Deck(Random.shuffle(cards))
  }

  def length: Int = cards.length
  
  def getCards: List[ICard] = cards
  /**
   *
   * @param n is the number of cards to draw
   * @return the drawn cards and the remaining deck
   */
  def draw(n: Int = 1): (List[ICard], IDeck) = {
    (cards.takeRight(n), Deck(cards.dropRight(n)))
  }

  override def toString: String = {
    s"[ ${cards.mkString(", ")} ]"
  }
}
