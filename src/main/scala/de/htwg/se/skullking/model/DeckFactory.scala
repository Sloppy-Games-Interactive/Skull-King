package de.htwg.se.skullking.model

object DeckFactory {
  private val amountOfNormalCards = List.range(1, 15)
  private val amountOfJoker: Int = 1
  private val amountOfPirate: Int = 5
  private val amountOfMermaid: Int = 2
  private val amountOfSkullKing: Int = 1
  private val amountOfEscape: Int = 5

  private val normalSuits = List(Suit.Red, Suit.Blue, Suit.Yellow, Suit.Black)
  private val jokerCards = List.fill(amountOfJoker)(Card(SpecialSuit.Joker, 0))
  private val specialCards = List.fill(amountOfPirate)(Card(SpecialSuit.Pirate, 0)) ++
    List.fill(amountOfMermaid)(Card(SpecialSuit.Mermaid, 0)) ++
    List.fill(amountOfSkullKing)(Card(SpecialSuit.SkullKing, 0)) ++
    List.fill(amountOfEscape)(Card(SpecialSuit.Escape, 0))

  def apply(kind: String): Deck = kind match {
    case "specials" => withSpecials
    case "normal" => normalCards
    case "full" => fullDeck
    case _ => Deck()
  }

  private def normalCards: Deck = {
    val cards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield Card(suit, value)
    Deck(cards)
  }

  private def withSpecials: Deck = {
    val normalCards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield Card(suit, value)

    Deck(normalCards ++ specialCards)
  }

  private def fullDeck: Deck = {
    val normalCards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield Card(suit, value)

    Deck(normalCards ++ jokerCards ++ specialCards)
  }
}
