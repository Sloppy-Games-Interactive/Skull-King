package de.htwg.se.skullking.model.DeckComponent

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.Suit
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.Deck
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.JsObject

class DeckSpec extends  AnyWordSpec{
  "Deck" should {
    "be shuffleable" in {
      val deck: IDeck = Deck(List(CardFactory(Suit.Red, 1), CardFactory(Suit.Red, 2), CardFactory(Suit.Red, 3), CardFactory(Suit.Red, 4), CardFactory(Suit.Red, 5)))
      val shuffled = deck.shuffle()

      deck.getCards should contain theSameElementsAs shuffled.getCards
      deck.getCards should not be shuffled.getCards
    }
    "be drawable" in {
      val deck: IDeck = Deck(List(CardFactory(Suit.Red, 1), CardFactory(Suit.Red, 2), CardFactory(Suit.Red, 3), CardFactory(Suit.Red, 4), CardFactory(Suit.Red, 5)))
      val (card, newDeck) = deck.draw()
      val (drawnCards, newDeck2) = deck.draw(5)

      newDeck.getCards.length should be (deck.getCards.length - 1)
      newDeck.getCards should not contain card
      drawnCards.length should be (5)
      newDeck2.getCards.length should be (deck.getCards.length - 5)
      newDeck2.getCards should not contain drawnCards
    }
    "be printable" in {
      val card1 = CardFactory(Suit.Red, 1)
      val printDeck = Deck(List(card1))

      printDeck.toString() should be ("[ \uD83D\uDFE5 1 ]")
    }

    "be serializable as json" in {
      val cards = List(CardFactory(Suit.Red, 1), CardFactory(Suit.Red, 2), CardFactory(Suit.Red, 3), CardFactory(Suit.Red, 4), CardFactory(Suit.Red, 5))
      val deck: IDeck = Deck(cards)
      val json = deck.toJson

      (json \ "cards").as[List[JsObject]] should contain theSameElementsAs cards.map(_.toJson)

      val newDeck = DeckDeserializer.fromJson(json)

      deck.getCards should contain theSameElementsAs newDeck.getCards
    }
  }
}
