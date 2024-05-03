package de.htwg.se.skullking.model

trait ReadableCard {
  def readable: String

  def cardType: CardType
}
