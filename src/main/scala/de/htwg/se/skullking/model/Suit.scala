package de.htwg.se.skullking.model

enum Suit(val color: String, val cardType: CardType, val readable: String) extends ReadableCard {
  case Red extends Suit("red", CardType.Standard, "🟥")
  case Yellow extends Suit("yellow", CardType.Standard, "🟡")
  case Blue extends Suit("blue", CardType.Standard, "🔷")
  case Black extends Suit("black", CardType.Trump, "☠️")
}
