package de.htwg.se.skullking.model.HandComponent

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.Suit
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.Deck
import de.htwg.se.skullking.model.DeckComponent.IDeck
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.Hand
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class HandSpec extends AnyWordSpec {
  "Hand" should {
    "have count" in {
      val r1 = CardFactory(Suit.Red, 1)
      val r2 = CardFactory(Suit.Red, 2)
      val cards = List(r1, r2)
      val h = new Hand(cards)

      h.count shouldEqual 2
    }
    "play a card" in {
      val r1 = CardFactory(Suit.Red, 1)
      val r2 = CardFactory(Suit.Red, 2)
      val cards = List(r1, r2)
      val h = new Hand(cards)
      val (playedCard, newHand) = h.play(1)

      playedCard shouldEqual r2
      newHand.cards should not contain r2
    }
    "draw from deck" in {
      val deck: IDeck = Deck(List(CardFactory(Suit.Red, 1), CardFactory(Suit.Red, 2), CardFactory(Suit.Red, 3), CardFactory(Suit.Red, 4), CardFactory(Suit.Red, 5)))
      val h = new Hand()
      val (newDeck, newHand) = h.drawFromDeck(deck.shuffle(), 2)

      newDeck.getCards should not contain theSameElementsAs(newHand.cards)
      newHand.cards should have length 2
    }
    "print hand" in {
      val r1 = CardFactory(Suit.Red, 1)
      val r2 = CardFactory(Suit.Red, 2)
      val cards = List(r1, r2)
      val h = new Hand(cards)

      h.toString.replaceAll(" ", "") shouldEqual "1:\uD83D\uDFE51;2:\uD83D\uDFE52"
    }
  }
}
