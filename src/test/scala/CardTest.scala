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

    "be comparable (Suit|Suit)" in {
      val r1 = Card(Suit.Red, 1)
      val r2 = Card(Suit.Red, 2)

      assert(r1 < r2)
      assert(r2 > r1)
      assert(r1 == r1)
      assert(r1 <= r1)
      assert(r1 >= r1)
      assert(r1 != r2)
      assert(r1.compare(r2) == -1)
      assert(r2.compare(r1) == 1)
      assert(r1.compare(r1) == 0)
    }

    "be comparable (Suit|Trump)" in {
      val r1 = Card(Suit.Red, 1)
      val t1 = Card(Suit.Black, 1)

      assert(r1 < t1)
      assert(t1 > r1)
      assert(r1 == r1)
      assert(r1 <= r1)
      assert(r1 >= r1)
      assert(r1 != t1)
      assert(r1.compare(t1) == -1)
      assert(t1.compare(r1) == 1)
    }

    "be comparable (Suit|Special)" in {
      val r1 = Card(Suit.Red, 1)
      val j1 = Card(SpecialCard.Joker, 1)

      assert(r1 < j1)
      assert(j1 > r1)
      assert(r1 == r1)
      assert(r1 <= r1)
      assert(r1 >= r1)
      assert(r1 != j1)
      assert(r1.compare(j1) == -1)
      assert(j1.compare(r1) == 1)
    }

    "be comparable (Special|Special)" in {
      val j1 = Card(SpecialCard.Joker, 1)
      val p2 = Card(SpecialCard.Pirate, 1)

      // TODO: expand this test once specials logic is implemented
      assert(j1 > p2)
    }

    "be identifiable as Trump" in {
      val card = Card(Suit.Black, 1)
      val r1 = Card(Suit.Red, 1)
      val j1 = Card(SpecialCard.Joker, 1)
      assert(card.isTrump)
      assert(!r1.isTrump)
      assert(!j1.isTrump)
    }

    "be identifiable as Special" in {
      val card = Card(SpecialCard.Joker, 1)
      val r1 = Card(Suit.Red, 1)
      val b1 = Card(Suit.Black, 1)
      assert(card.isSpecial)
      assert(!r1.isSpecial)
      assert(!b1.isSpecial)
    }
  }
}