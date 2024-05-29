package de.htwg.se.skullking.model.deck

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class DeckFactorySpec extends AnyWordSpec{

  "DeckFactory" should {
    "be able to create a deck with specials cards" in {
      val deck = DeckFactory(DeckContent.specials)
      deck.length should be (69)
    }

    "be able to create a deck with normal cards" in {
      val deck = DeckFactory(DeckContent.normal)
      deck.length should be (56)
    }

    "be able to create a deck with all cards" in {
      val deck = DeckFactory(DeckContent.full)
      deck.length should be (70)
    }

    "be able to create a empty deck by default" in {
      val deck = DeckFactory()
      deck.length should be (0)
    }

    "be able to create a empty deck" in {
      val deck = DeckFactory(DeckContent.empty)
      deck.length should be (0)
    }
  }
}
