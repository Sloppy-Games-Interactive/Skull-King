package de.htwg.se.skullking.model

enum SpecialCard(val readable: String, val cardType: CardType) extends ReadableCard {
  case Joker extends SpecialCard("🤡", CardType.Special)
  case Mermaid extends SpecialCard("🧜", CardType.Special)
  case SkullKing extends SpecialCard("👑", CardType.Special)
  case Pirate extends SpecialCard("🍻", CardType.Special)
  case Escape extends SpecialCard("🚣", CardType.Special)
}
