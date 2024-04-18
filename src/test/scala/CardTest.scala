import org.scalatest.wordspec.AnyWordSpec

class CardTest extends AnyWordSpec {
  "Card" should {
    "have a readable" in {
      val card = Card(1, Suit.Red)
      assert(card.suit.readable == "\uD83D\uDFE5")
    }

    "correctly display its suit and value in its toString method" in {
      val card = Card(1, Suit.Red)
      assert(card.toString == "Card: 🟥 1")
    }

    "correctly display its suit when it is a special card" in {
      val card = Card(0, SpecialCard.Joker)
      assert(card.toString == "Card: 🤡 0")
    }

  }
}