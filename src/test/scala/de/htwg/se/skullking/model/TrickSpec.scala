package de.htwg.se.skullking.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TrickSpec extends AnyWordSpec {
  "A Trick" when {
    val r1 = Card(Suit.Red, 1)
    val r2 = Card(Suit.Red, 2)
    val r14 = Card(Suit.Red, 14)

    val y1 = Card(Suit.Yellow, 1)
    val y2 = Card(Suit.Yellow, 2)
    val y14 = Card(Suit.Yellow, 14)

    val b1 = Card(Suit.Black, 1)
    val b2 = Card(Suit.Black, 2)
    val b14 = Card(Suit.Black, 14)

    val e = Card(Suit.Escape)
    val p = Card(Suit.Pirate)
    val m = Card(Suit.Mermaid)
    val sk = Card(Suit.SkullKing)

    "playing any card" should {
      val t0 = Trick()
      val t1 = t0.play(r1, 0)
      val t2 = t1.play(r2, 1)

      "have list of players" in {
        t0.players shouldBe List()
        t1.players shouldBe List(0)
        t2.players shouldBe List(0, 1)
      }

      "have list of cards" in {
        t0.cards shouldBe List()
        t1.cards shouldBe List(r1)
        t2.cards shouldBe List(r1, r2)
      }

      "have leading suit" in {
        t0.leadSuit shouldBe None
        t1.leadSuit shouldBe Suit.Red
        t2.leadSuit shouldBe Suit.Red
      }
    }

    "playing only standard suits" should {
      val trick = Trick().play(r1, 0)

      "be won by highest card of leading suit" in {
        trick.play(r2, 1).winner shouldBe 1
        trick.play(y14, 1).winner shouldBe 0
      }
    }

    "playing trump cards" should {
      val trick = Trick().play(r1, 0)

      "be won by highest trump card" in {
        trick.play(b1, 1).winner shouldBe 1
        trick.play(r14, 1).play(b1, 2).winner shouldBe 2
        trick.play(b1, 1).play(b2, 2).winner shouldBe 2
      }
    }

    "playing special cards" should {
      val trick = Trick().play(r14, 0).play(b14, 1)

      "be won by skull king" in {
        trick.play(sk, 2).winner shouldBe 2
        trick.play(p, 3).play(sk, 2).winner shouldBe 2
      }
      "be won by pirate" in {
        trick.play(p, 2).winner shouldBe 2
        trick.play(p, 2).play(m, 3).winner shouldBe 2
        trick.play(p, 2).play(p, 3).winner shouldBe 2
      }
      "be won by mermaid" in {
        trick.play(m, 2).winner shouldBe 2
        trick.play(m, 2).play(sk, 3).winner shouldBe 2
        trick.play(m, 2).play(sk, 3).play(p, 4).winner shouldBe 2
        trick.play(m, 2).play(m, 3).winner shouldBe 2
      }
    }

    "playing escape cards" should {
      val escapeTrick = Trick().play(e, 0)

      "be won by escape" in {
        escapeTrick.play(e, 1).winner shouldBe 0
      }
      "have no leading suit if no non-escape card is present" in {
        escapeTrick.leadSuit shouldBe None
        escapeTrick.play(e, 1).leadSuit shouldBe None
        escapeTrick.play(m, 1).leadSuit shouldBe None
      }
      "have first non-escape card as leading suit" in {
        val t = escapeTrick.play(r1, 1)
        t.winner shouldBe 1
        t.leadSuit shouldBe Suit.Red
      }
    }

    "calculating bonus points" should {
      "give 10 points for each standard suit 14 card" in {
        Trick().play(r14, 0).calculateBonusPoints shouldBe 10
        Trick().play(r14, 0).play(y14, 1).calculateBonusPoints shouldBe 20
      }
      "give 20 points for trump 14 card" in {
        Trick().play(b14, 0).calculateBonusPoints shouldBe 20
      }
      "give 40 points for skull king + mermaid" in {
        Trick().play(sk, 0).play(m, 1).calculateBonusPoints shouldBe 40
      }
      "give 30 points per pirate if skull king present" in {
        Trick().play(sk, 0).play(p, 1).calculateBonusPoints shouldBe 30
        Trick().play(sk, 0).play(p, 1).play(p, 2).calculateBonusPoints shouldBe 60
      }
      "give 20 points per mermaid if at least one pirate present" in {
        Trick().play(p, 0).play(m, 1).calculateBonusPoints shouldBe 20
        Trick().play(p, 0).play(m, 1).play(m, 2).calculateBonusPoints shouldBe 40
      }
      "only give mermaid bonus if mermaid + skull king + pirate present" in {
        Trick().play(p, 0).play(m, 1).play(sk, 2).calculateBonusPoints shouldBe 40
        Trick().play(p, 0).play(m, 1).play(sk, 2).play(m, 3).calculateBonusPoints shouldBe 40
      }
    }
  }
}
