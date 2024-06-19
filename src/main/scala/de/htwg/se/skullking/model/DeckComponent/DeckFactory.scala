package de.htwg.se.skullking.model.DeckComponent

enum DeckContent {
  case specials
  case normal
  case full
  case empty
}

trait IDeckFactory {
  def apply(kind: DeckContent = DeckContent.empty): IDeck
}

object DeckFactory {
  private var factory: IDeckFactory = DeckBaseFactory()

  def apply(kind: DeckContent = DeckContent.empty): IDeck = factory.apply(kind)
  
  def setFactory(newFactory: IDeckFactory): Unit = {
    factory = newFactory
  }
}
