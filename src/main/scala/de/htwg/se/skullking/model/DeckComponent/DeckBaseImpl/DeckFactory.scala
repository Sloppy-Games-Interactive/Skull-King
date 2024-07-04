package de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl

import de.htwg.se.skullking.model.CardComponent.{ICard, ICardFactory, IJokerCard, Suit}
import de.htwg.se.skullking.model.DeckComponent.*
import de.htwg.se.skullking.modules.Default.given

import scala.collection.immutable.List

object DeckFactory extends IDeckFactory {
  private val amountOfNormalCards = List.range(1, 15)
  private val amountOfJoker: Int = 1
  private val amountOfPirate: Int = 5
  private val amountOfMermaid: Int = 2
  private val amountOfSkullKing: Int = 1
  private val amountOfEscape: Int = 5

  private val normalSuits = List(Suit.Red, Suit.Blue, Suit.Yellow, Suit.Black)

  def apply(kind: DeckContent = DeckContent.empty): IDeck = kind match {
    case DeckContent.specials => withSpecials(using summon[ICardFactory])
    case DeckContent.normal => normalCards(using summon[ICardFactory])
    case DeckContent.full => fullDeck(using summon[ICardFactory])
    case DeckContent.empty => Deck()
  }

  def apply(cards: List[ICard]): IDeck = Deck(cards)
  
  private def normalCards(using cardFactory: ICardFactory): IDeck = {
    val cards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield cardFactory(suit, value)
    Deck(cards)
  }

  private def withSpecials(using cardFactory: ICardFactory): IDeck = {
    val normalCards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield cardFactory(suit, value)

    val specialCards = List.fill(amountOfPirate)(cardFactory(Suit.Pirate)) ++
      List.fill(amountOfMermaid)(cardFactory(Suit.Mermaid)) ++
      List.fill(amountOfSkullKing)(cardFactory(Suit.SkullKing)) ++
      List.fill(amountOfEscape)(cardFactory(Suit.Escape))

    Deck(normalCards ++ specialCards)
  }

  private def fullDeck(using cardFactory: ICardFactory): IDeck = {
    val normalCards = for {
      suit <- normalSuits
      value <- amountOfNormalCards
    } yield cardFactory(suit, value)

    val jokerCards = List.fill(amountOfJoker)(summon[IJokerCard])
    val specialCards = List.fill(amountOfPirate)(cardFactory(Suit.Pirate)) ++
      List.fill(amountOfMermaid)(cardFactory(Suit.Mermaid)) ++
      List.fill(amountOfSkullKing)(cardFactory(Suit.SkullKing)) ++
      List.fill(amountOfEscape)(cardFactory(Suit.Escape))

    Deck(normalCards ++ jokerCards ++ specialCards)
  }
}
