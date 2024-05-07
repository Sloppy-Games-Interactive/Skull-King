package de.htwg.se.skullking.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends  AnyWordSpec{
  "Deck" should {
    "be shuffleable" in {
      val deck: Deck = Deck(List(Card(Suit.Red, 1), Card(Suit.Red, 2), Card(Suit.Red, 3), Card(Suit.Red, 4), Card(Suit.Red, 5)))
      val shuffled = deck.shuffle()

      deck.cards should contain theSameElementsAs shuffled.cards
      deck.cards should not be shuffled.cards
    }
    "be drawable" in {
      val deck: Deck = Deck(List(Card(Suit.Red, 1), Card(Suit.Red, 2), Card(Suit.Red, 3), Card(Suit.Red, 4), Card(Suit.Red, 5)))
      val (card, newDeck) = deck.draw()
      val (drawnCards, newDeck2) = deck.draw(5)

      newDeck.cards.length should be (deck.cards.length - 1)
      newDeck.cards should not contain card
      drawnCards.length should be (5)
      newDeck2.cards.length should be (deck.cards.length - 5)
      newDeck2.cards should not contain drawnCards
    }
    "be printable" in {
      val card1 = Card(Suit.Red, 1)
      val printDeck = Deck(List(card1))

      printDeck.toString() should be ("[ \uD83D\uDFE5 1 ]")
    }
  }
}
