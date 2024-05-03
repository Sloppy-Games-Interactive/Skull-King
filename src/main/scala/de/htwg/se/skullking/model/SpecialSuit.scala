package de.htwg.se.skullking.model

enum SpecialSuit(val readable: String, val cardType: CardType) extends SuitInterface {
  case Joker extends SpecialSuit("🤡", CardType.Special)
  case Mermaid extends SpecialSuit("🧜", CardType.Special)
  case SkullKing extends SpecialSuit("👑", CardType.Special)
  case Pirate extends SpecialSuit("🍻", CardType.Special)
  case Escape extends SpecialSuit("🚣", CardType.Special)
}
