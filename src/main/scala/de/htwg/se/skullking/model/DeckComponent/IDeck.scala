package de.htwg.se.skullking.model.DeckComponent

import de.htwg.se.skullking.model.CardComponent.ICard

trait IDeck(cards: List[ICard] = List()) {
  /**
   * shuffle cards in the card list
   *
   * @return mixed card list
   */
  def shuffle(): IDeck

  def length: Int

  def getCards: List[ICard]
  /**
   *
   * @param n is the number of cards to draw
   * @return the drawn cards and the remaining deck
   */
  def draw(n: Int = 1): (List[ICard], IDeck)
}
