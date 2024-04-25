import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class HandSpec extends AnyWordSpec {
  "Hand" should {
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
      val h = new Hand()
      val (newDeck, newHand) = h.drawFromDeck(fullDeck.shuffle(), 2)

      newDeck.cards should not contain theSameElementsAs(newHand.cards)
      newHand.cards should have length 2
    }
    "print hand" in {
      val r1 = Card(Suit.Red, 1)
      val r2 = Card(Suit.Red, 2)
      val cards = List(r1, r2)
      val h = new Hand(cards)

      h.toString shouldEqual "1: \uD83D\uDFE5 1\n2: \uD83D\uDFE5 2"
    }
  }
}
