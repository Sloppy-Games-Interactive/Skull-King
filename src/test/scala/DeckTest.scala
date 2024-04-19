//import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec

class DeckTest extends  AnyWordSpec{
  "Deck" should {
    "have 61 cards" in {
      assert(fullDeck.cards.length == 61)
    }
    "have 56 cards of each suit" in {
      assert(fullDeck.cards.count(_.suit == Suit.Red) == 14)
      assert(fullDeck.cards.count(_.suit == Suit.Black) == 14)
      assert(fullDeck.cards.count(_.suit == Suit.Blue) == 14)
      assert(fullDeck.cards.count(_.suit == Suit.Yellow) == 14)
    }
    "have 5 special cards" in {
      assert(fullDeck.cards.count(card => card.suit == SpecialCard.Joker) == 1)
      assert(fullDeck.cards.count(card => card.suit == SpecialCard.Mermaid) == 1)
      assert(fullDeck.cards.count(card => card.suit == SpecialCard.SkullKing) == 1)
      assert(fullDeck.cards.count(card => card.suit == SpecialCard.Pirate) == 1)
      assert(fullDeck.cards.count(card => card.suit == SpecialCard.Escape) == 1)
    }
    "be shuffleable" in {
      val shuffled = fullDeck.shuffle()

      assert(fullDeck.cards != shuffled.cards)
    }
    "be drawable" in {
      val (card, newDeck) = fullDeck.draw()

      assert(newDeck.cards.length == 60)
      assert(!newDeck.cards.contains(card))
    }
    "be printable" in {
      val card1 = Card(Suit.Red, 1)
      val printDeck = Deck(List(card1))

      assert(printDeck.toString() == "[ \uD83D\uDFE5 1 ]")
    }
  }
}
