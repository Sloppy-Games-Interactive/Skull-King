package de.htwg.se.skullking.model

import de.htwg.se.skullking.model.CardType.CardType

enum Suit(val readable: String, val cardType: CardType) {
  case Red extends Suit("🟥", CardType.Standard)
  case Yellow extends Suit("🟡", CardType.Standard)
  case Blue extends Suit("🔷", CardType.Standard)
  case Black extends Suit("☠️", CardType.Trump)
  case Escape extends Suit("🏝️", CardType.Special)
  case SkullKing extends Suit("💀", CardType.Special)
  case Pirate extends Suit("🏴‍☠️", CardType.Special)
  case Mermaid extends Suit("🧜", CardType.Special)
  case Joker extends Suit("🃏", CardType.Special)
}
