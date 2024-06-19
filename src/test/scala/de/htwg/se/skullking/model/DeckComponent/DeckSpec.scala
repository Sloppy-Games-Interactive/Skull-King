package de.htwg.se.skullking.model.DeckComponent

import de.htwg.se.skullking.model.CardComponent.{Card, Suit}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends  AnyWordSpec{
  "Deck" should {
    "be shuffleable" in {
      val deck: IDeck = Deck(List(Card(Suit.Red, 1), Card(Suit.Red, 2), Card(Suit.Red, 3), Card(Suit.Red, 4), Card(Suit.Red, 5)))
      val shuffled = deck.shuffle()

      deck.getCards should contain theSameElementsAs shuffled.getCards
      deck.getCards should not be shuffled.getCards
    }
    "be drawable" in {
      val deck: IDeck = Deck(List(Card(Suit.Red, 1), Card(Suit.Red, 2), Card(Suit.Red, 3), Card(Suit.Red, 4), Card(Suit.Red, 5)))
      val (card, newDeck) = deck.draw()
      val (drawnCards, newDeck2) = deck.draw(5)

      newDeck.getCards.length should be (deck.getCards.length - 1)
      newDeck.getCards should not contain card
      drawnCards.length should be (5)
      newDeck2.getCards.length should be (deck.getCards.length - 5)
      newDeck2.getCards should not contain drawnCards
    }
    "be printable" in {
      val card1 = Card(Suit.Red, 1)
      val printDeck = Deck(List(card1))

      printDeck.toString() should be ("[ \uD83D\uDFE5 1 ]")
    }
  }
}
