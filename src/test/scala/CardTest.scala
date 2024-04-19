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

    "be comparable via <" in {
      val card1 = Card(Suit.Red, 1)
      val card2 = Card(Suit.Red, 2)
      val card3 = Card(Suit.Black, 1)
      val card4 = Card(SpecialCard.Joker, 0)
      assert(card1 < card2)
      assert(card1 < card3)
      assert(card1 < card4)
      assert(card3 < card4)
    }

    "be comparable via <=" in {
      val card1 = Card(Suit.Red, 1)
      val card2 = Card(Suit.Red, 2)
      val card3 = Card(Suit.Black, 1)
      val card4 = Card(SpecialCard.Joker, 0)
      assert(card1 <= card2)
      assert(card1 <= card3)
      assert(card1 <= card4)
      assert(card3 <= card4)
      assert(card1 <= card1)
    }

    "be comparable via >" in {
      val card1 = Card(Suit.Red, 1)
      val card2 = Card(Suit.Red, 2)
      val card3 = Card(Suit.Black, 1)
      val card4 = Card(SpecialCard.Joker, 0)
      assert(card2 > card1)
      assert(card3 > card1)
      assert(card4 > card1)
      assert(card4 > card3)
    }

    "be comparable via >=" in {
      val card1 = Card(Suit.Red, 1)
      val card2 = Card(Suit.Red, 2)
      val card3 = Card(Suit.Black, 1)
      val card4 = Card(SpecialCard.Joker, 0)
      assert(card2 >= card1)
      assert(card3 >= card1)
      assert(card4 >= card1)
      assert(card4 >= card3)
      assert(card1 >= card1)
    }

    "be comparable via ==" in {
      val card1 = Card(Suit.Red, 1)
      val card2 = Card(Suit.Red, 1)
      assert(card1 == card2)
    }

    "be comparable via !=" in {
      val card1 = Card(Suit.Red, 1)
      val card2 = Card(Suit.Red, 2)
      assert(card1 != card2)
    }

    "be comparable via compare" in {
      val card1 = Card(Suit.Red, 1)
      val card2 = Card(Suit.Red, 2)
      val card3 = Card(Suit.Red, 1)
      assert(card1.compare(card2) == -1)
      assert(card2.compare(card1) == 1)
      assert(card1.compare(card3) == 0)
    }
  }
}