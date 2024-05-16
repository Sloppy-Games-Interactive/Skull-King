package de.htwg.se.skullking.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class HandSpec extends AnyWordSpec {
  "Hand" should {
    "have count" in {
      val r1 = Card(Suit.Red, 1)
      val r2 = Card(Suit.Red, 2)
      val cards = List(r1, r2)
      val h = new Hand(cards)

      h.count shouldEqual 2
    }
    "play a card" in {
      val r1 = Card(Suit.Red, 1)
      val r2 = Card(Suit.Red, 2)
      val cards = List(r1, r2)
      val h = new Hand(cards)
      val (playedCard, newHand) = h.play(2)

      playedCard shouldEqual r2
      newHand.cards should not contain r2
    }
    "draw from deck" in {
      val deck: Deck = Deck(List(Card(Suit.Red, 1), Card(Suit.Red, 2), Card(Suit.Red, 3), Card(Suit.Red, 4), Card(Suit.Red, 5)))
      val h = new Hand()
      val (newDeck, newHand) = h.drawFromDeck(deck.shuffle(), 2)

      newDeck.getCards should not contain theSameElementsAs(newHand.cards)
      newHand.cards should have length 2
    }
    "print hand" in {
      val r1 = Card(Suit.Red, 1)
      val r2 = Card(Suit.Red, 2)
      val cards = List(r1, r2)
      val h = new Hand(cards)

      h.toString.replaceAll(" ", "") shouldEqual "1:\uD83D\uDFE51\n2:\uD83D\uDFE52"
    }
  }
}
