package de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl

import com.google.inject.Inject
//import de.htwg.se.skullking.modules.Default.given

import de.htwg.se.skullking.model.CardComponent.{ICardFactory, IJokerCard, Suit}
import de.htwg.se.skullking.model.DeckComponent.*

import scala.collection.immutable.List

class DeckFactory @Inject(CardFactory: ICardFactory, jokerCard: IJokerCard) extends IDeckFactory {
  
  private val amountOfNormalCards = List.range(1, 15)
  private val amountOfJoker: Int = 1
  private val amountOfPirate: Int = 5
  private val amountOfMermaid: Int = 2
  private val amountOfSkullKing: Int = 1
  private val amountOfEscape: Int = 5

  private val normalSuits = List(Suit.Red, Suit.Blue, Suit.Yellow, Suit.Black)
  private val jokerCards = List.fill(amountOfJoker)(jokerCard)
  private val specialCards = List.fill(amountOfPirate)(CardFactory(Suit.Pirate)) ++
    List.fill(amountOfMermaid)(CardFactory(Suit.Mermaid)) ++
    List.fill(amountOfSkullKing)(CardFactory(Suit.SkullKing)) ++
    List.fill(amountOfEscape)(CardFactory(Suit.Escape))

  def apply(kind: DeckContent = DeckContent.empty): IDeck = kind match {
    case DeckContent.specials => withSpecials
    case DeckContent.normal => normalCards
    case DeckContent.full => fullDeck
    case DeckContent.empty => Deck()
  }

  private def normalCards: IDeck = {
    val cards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield CardFactory(suit, value)
    Deck(cards)
  }

  private def withSpecials: IDeck = {
    val normalCards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield CardFactory(suit, value)

    Deck(normalCards ++ specialCards)
  }

  private def fullDeck: IDeck = {
    val normalCards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield CardFactory(suit, value)

    Deck(normalCards ++ jokerCards ++ specialCards)
  }
}
