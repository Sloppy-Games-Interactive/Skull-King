package de.htwg.se.skullking.model.CardComponent

import de.htwg.se.skullking.model.CardComponent.Suit

trait ICard {
  val suit: Suit

  def isSpecial: Boolean

  def isTrump: Boolean
}
