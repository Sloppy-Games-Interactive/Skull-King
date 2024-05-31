package de.htwg.se.skullking.model.trick

import de.htwg.se.skullking.model.card.{Card, Suit}
import de.htwg.se.skullking.model.player.Player
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
    
    "nobody plays" should {
      "have no winner" in {
        Trick().winner shouldBe None
      }
    }

    "playing any card" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val t0 = Trick()
      val t1 = t0.play(r1, p1)
      val t2 = t1.play(r2, p2)

      "have list of players" in {
        t0.players shouldBe List()
        t1.players shouldBe List(p1)
        t2.players shouldBe List(p1, p2)
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
      val p1 = Player("p1")
      val p2 = Player("p2")
      val trick = Trick().play(r1, p1)

      "be won by highest card of leading suit" in {
        trick.play(r2, p2).winner shouldBe Some(p2)
        trick.play(y14, p2).winner shouldBe Some(p1)
      }
    }

    "playing trump cards" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val p3 = Player("p3")
      val trick = Trick().play(r1, p1)

      "be won by highest trump card" in {
        trick.play(b1, p2).winner shouldBe Some(p2)
        trick.play(r14, p2).play(b1, p3).winner shouldBe Some(p3)
        trick.play(b1, p2).play(b2, p3).winner shouldBe Some(p3)
      }
    }

    "playing special cards" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val p3 = Player("p3")
      val p4 = Player("p4")
      val p5 = Player("p5")
      val trick = Trick().play(r14, p1).play(b14, p2)

      "be won by skull king" in {
        trick.play(sk, p3).winner shouldBe Some(p3)
        trick.play(p, p4).play(sk, p3).winner shouldBe Some(p3)
      }
      "be won by pirate" in {
        trick.play(p, p3).winner shouldBe Some(p3)
        trick.play(p, p3).play(m, p4).winner shouldBe Some(p3)
        trick.play(p, p3).play(p, p4).winner shouldBe Some(p3)
      }
      "be won by mermaid" in {
        trick.play(m, p3).winner shouldBe Some(p3)
        trick.play(m, p3).play(sk, p4).winner shouldBe Some(p3)
        trick.play(m, p3).play(sk, p4).play(p, p5).winner shouldBe Some(p3)
        trick.play(m, p3).play(m, p4).winner shouldBe Some(p3)
      }
    }

    "playing escape cards" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val escapeTrick = Trick().play(e, p1)

      "be won by escape" in {
        escapeTrick.play(e, p2).winner shouldBe Some(p1)
      }
      "have no leading suit if no non-escape card is present" in {
        escapeTrick.leadSuit shouldBe None
        escapeTrick.play(e, p2).leadSuit shouldBe None
        escapeTrick.play(m, p2).leadSuit shouldBe None
      }
      "have first non-escape card as leading suit" in {
        val p1 = Player("p1")
        val t = escapeTrick.play(r1, p1)
        t.winner shouldBe Some(p1)
        t.leadSuit shouldBe Suit.Red
      }
    }

    "calculating bonus points" should {
      "give 10 points for each standard suit 14 card" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        Trick().play(r14, p1).calculateBonusPoints shouldBe 10
        Trick().play(r14, p1).play(y14, p2).calculateBonusPoints shouldBe 20
      }
      "give 20 points for trump 14 card" in {
        val p1 = Player("p1")
        Trick().play(b14, p1).calculateBonusPoints shouldBe 20
      }
      "give 40 points for skull king + mermaid" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        Trick().play(sk, p1).play(m, p2).calculateBonusPoints shouldBe 40
      }
      "give 30 points per pirate if skull king present" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        val p3 = Player("p3")
        Trick().play(sk, p1).play(p, p2).calculateBonusPoints shouldBe 30
        Trick().play(sk, p1).play(p, p2).play(p, p3).calculateBonusPoints shouldBe 60
      }
      "give 20 points per mermaid if at least one pirate present" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        val p3 = Player("p3")
        Trick().play(p, p1).play(m, p2).calculateBonusPoints shouldBe 20
        Trick().play(p, p1).play(m, p2).play(m, p3).calculateBonusPoints shouldBe 40
      }
      "only give mermaid bonus if mermaid + skull king + pirate present" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        val p3 = Player("p3")
        val p4 = Player("p4")
        Trick().play(p, p1).play(m, p2).play(sk, p3).calculateBonusPoints shouldBe 40
        Trick().play(p, p1).play(m, p2).play(sk, p3).play(m, p4).calculateBonusPoints shouldBe 40
      }
    }
  }
}
