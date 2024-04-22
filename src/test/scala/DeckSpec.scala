import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class DeckSpec extends  AnyWordSpec{
  "Deck" should {
    "have 61 cards" in {
      fullDeck.cards.length should be (61)
    }
    "have 56 cards of each suit" in {
      fullDeck.cards.count(_.suit == Suit.Red) should be (14)
      fullDeck.cards.count(_.suit == Suit.Black) should be (14)
      fullDeck.cards.count(_.suit == Suit.Blue) should be (14)
      fullDeck.cards.count(_.suit == Suit.Yellow) should be (14)
    }
    "have 5 special cards" in {
      fullDeck.cards.count(_.suit == SpecialCard.Joker) should be (1)
      fullDeck.cards.count(_.suit == SpecialCard.Mermaid) should be (1)
      fullDeck.cards.count(_.suit == SpecialCard.SkullKing) should be (1)
      fullDeck.cards.count(_.suit == SpecialCard.Pirate) should be (1)
      fullDeck.cards.count(_.suit == SpecialCard.Escape) should be (1)
    }
    "be shuffleable" in {
      val shuffled = fullDeck.shuffle()

      fullDeck.cards should contain theSameElementsAs shuffled.cards
      fullDeck.cards should not be shuffled.cards
    }
    "be drawable" in {
      val (card, newDeck) = fullDeck.draw()
      val (drawnCards, newDeck2) = fullDeck.draw(5)

      newDeck.cards.length should be (fullDeck.cards.length - 1)
      newDeck.cards should not contain card
      drawnCards.length should be (5)
      newDeck2.cards.length should be (fullDeck.cards.length - 5)
      newDeck2.cards should not contain drawnCards
    }
    "be printable" in {
      val card1 = Card(Suit.Red, 1)
      val printDeck = Deck(List(card1))

      printDeck.toString() should be ("[ \uD83D\uDFE5 1 ]")
    }
  }
}
