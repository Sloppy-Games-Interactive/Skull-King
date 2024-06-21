package de.htwg.se.skullking.model.PlayerComponent

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.DeckComponent.IDeck

/**
 * Hand class for
 *
 * @param cards take a list of playable cards, default empty list of card
 */
trait IHand {
  val cards: List[ICard]
  def count: Int
  
  def indexOf(card: ICard): Option[Int]

  def play(idx: Int): (ICard, IHand)

  def drawFromDeck(deck: IDeck, n: Int): (IDeck, IHand)
}
