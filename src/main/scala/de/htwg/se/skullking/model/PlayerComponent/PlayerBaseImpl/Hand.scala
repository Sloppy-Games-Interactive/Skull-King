package de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.DeckComponent.IDeck
import de.htwg.se.skullking.model.PlayerComponent._

class Hand(val cards: List[ICard] = List()) extends IHand {
  def count: Int = cards.length

  def indexOf(card: ICard): Option[Int] = cards.indexOf(card) match {
    case -1 => None
    case idx => Some(idx)
  }

  def play(idx: Int): (ICard, IHand) = {
    val (before, after) = cards.splitAt(idx)
    (after.head, Hand(before ++ after.tail))
  }

  def drawFromDeck(deck: IDeck, n: Int): (IDeck, IHand) = {
    val (drawn, remaining) = deck.draw(n)
    (remaining, Hand(cards ++ drawn))
  }

  override def toString: String = {
    cards.zipWithIndex.map { case (card, idx) => s"${idx + 1}: $card" }.mkString("; ")
  }
}

object HandFactory extends IHandFactory {
  def apply(cards: List[ICard]): IHand = Hand(cards)
}
