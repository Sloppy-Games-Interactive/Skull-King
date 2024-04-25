import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class CardSpec extends AnyWordSpec {
  "Card" should {
    "have a readable" in {
      val card = Card(Suit.Red, 1)
      card.suit.readable should be("\uD83D\uDFE5")
    }

    "correctly display its suit and value in its toString method" in {
      val card = Card(Suit.Red, 1)
      card.toString should be("🟥 1")
    }

    "correctly display its suit when it is a special card" in {
      val card = Card(SpecialCard.Joker, 0)
      card.toString should be("🤡 0")
    }

    "be comparable (Suit|Suit)" in {
      val r1 = Card(Suit.Red, 1)
      val r2 = Card(Suit.Red, 2)

      r1.compare(r2) should be(-1)
      r2.compare(r1) should be(1)
      r1.compare(r1) should be(0)

      r1 should be < r2
      r2 should be > r1
      r1 should be <= r1
      r1 should be >= r1
    }

    "be comparable (Suit|Trump)" in {
      val r1 = Card(Suit.Red, 1)
      val t1 = Card(Suit.Black, 1)
      
      r1.compare(t1) should be(-1)
      t1.compare(r1) should be(1)
      
      r1 should be < t1
      t1 should be > r1
      r1 should be <= r1
      r1 should be >= r1
    }

    "be comparable (Suit|Special)" in {
      val r1 = Card(Suit.Red, 1)
      val j1 = Card(SpecialCard.Joker, 1)
      
      r1.compare(j1) should be(-1)
      j1.compare(r1) should be(1)
      
      r1 should be < j1
      j1 should be > r1
      r1 should be <= r1
      r1 should be >= r1
    }

    "be comparable (Special|Special)" in {
      val j1 = Card(SpecialCard.Joker, 1)
      val p2 = Card(SpecialCard.Pirate, 1)

      // TODO: expand this test once specials logic is implemented
      j1 should be > p2
    }

    "be identifiable as Trump" in {
      val card = Card(Suit.Black, 1)
      val r1 = Card(Suit.Red, 1)
      val j1 = Card(SpecialCard.Joker, 1)
      
      card.isTrump should be(true)
      r1.isTrump should be(false)
      j1.isTrump should be(false)
    }

    "be identifiable as Special" in {
      val card = Card(SpecialCard.Joker, 1)
      val r1 = Card(Suit.Red, 1)
      val b1 = Card(Suit.Black, 1)
      
      card.isSpecial should be(true)
      r1.isSpecial should be(false)
      b1.isSpecial should be(false)
    }
  }
}