package de.htwg.se.skullking.model

enum DeckContent {
  case specials
  case normal
  case full
  case empty
}

object DeckFactory {
  private val amountOfNormalCards = List.range(1, 15)
  private val amountOfJoker: Int = 1
  private val amountOfPirate: Int = 5
  private val amountOfMermaid: Int = 2
  private val amountOfSkullKing: Int = 1
  private val amountOfEscape: Int = 5

  private val normalSuits = List(Suit.Red, Suit.Blue, Suit.Yellow, Suit.Black)
  private val jokerCards = List.fill(amountOfJoker)(JokerCard())
  private val specialCards = List.fill(amountOfPirate)(Card(Suit.Pirate)) ++
    List.fill(amountOfMermaid)(Card(Suit.Mermaid)) ++
    List.fill(amountOfSkullKing)(Card(Suit.SkullKing)) ++
    List.fill(amountOfEscape)(Card(Suit.Escape))

  def apply(kind: DeckContent = DeckContent.empty): Deck = kind match {
    case DeckContent.specials => withSpecials
    case DeckContent.normal => normalCards
    case DeckContent.full => fullDeck
    case DeckContent.empty => Deck()
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
