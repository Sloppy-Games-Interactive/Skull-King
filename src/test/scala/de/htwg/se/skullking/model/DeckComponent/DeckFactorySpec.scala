package de.htwg.se.skullking.model.DeckComponent

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.{CardFactory, JokerCard}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.DeckFactory
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class DeckFactorySpec extends AnyWordSpec{

  "DeckFactory" should {
    "be able to create a deck with specials cards" in {
      val deck = DeckFactory(CardFactory(), JokerCard())(DeckContent.specials)
      deck.length should be (69)
    }

    "be able to create a deck with normal cards" in {
      val deck = DeckFactory(CardFactory(), JokerCard())(DeckContent.normal)
      deck.length should be (56)
    }

    "be able to create a deck with all cards" in {
      val deck = DeckFactory(CardFactory(), JokerCard())(DeckContent.full)
      deck.length should be (70)
    }

    "be able to create a empty deck by default" in {
      val deck = DeckFactory(CardFactory(), JokerCard())()
      deck.length should be (0)
    }

    "be able to create a empty deck" in {
      val deck = DeckFactory(CardFactory(), JokerCard())(DeckContent.empty)
      deck.length should be (0)
    }
  }
}
