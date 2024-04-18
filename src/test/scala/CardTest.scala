import org.scalatest.wordspec.AnyWordSpec

class CardTest extends AnyWordSpec {
  "Card" should {
    "have a readable" in {
      val card = Card(Suit.Red, 1)
      assert(card.suit.readable == "\uD83D\uDFE5")
    }

    "correctly display its suit and value in its toString method" in {
      val card = Card(Suit.Red, 1)
      assert(card.toString == "🟥 1")
    }

    "correctly display its suit when it is a special card" in {
      val card = Card(SpecialCard.Joker, 0)
      assert(card.toString == "🤡 0")
    }

  }
}