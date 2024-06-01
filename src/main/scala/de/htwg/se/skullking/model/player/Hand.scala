package de.htwg.se.skullking.model.player

import de.htwg.se.skullking.model.deck.Deck
import de.htwg.se.skullking.model.card.Card

/**
 * Hand class for
 *
 * @param cards take a list of playable cards, default empty list of card
 */
class Hand(val cards: List[Card] = List()) {
  def count: Int = cards.length

  def play(idx: Int): (Card, Hand) = {
    val (before, after) = cards.splitAt(idx)
    (after.head, Hand(before ++ after.tail))
  }

  def drawFromDeck(deck: Deck, n: Int): (Deck, Hand) = {
    val (drawn, remaining) = deck.draw(n)
    (remaining, Hand(cards ++ drawn))
  }

  override def toString: String = {
    cards.zipWithIndex.map { case (card, idx) => s"${idx + 1}: $card" }.mkString("; ")
  }
}
