package de.htwg.se.skullking.model.TrickComponent

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.Suit
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.Player
import de.htwg.se.skullking.model.trick.TrickComponent.TrickBaseImpl.Trick
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TrickSpec extends AnyWordSpec {
  "A Trick" when {
    val r1 = CardFactory(Suit.Red, 1)
    val r2 = CardFactory(Suit.Red, 2)
    val r14 = CardFactory(Suit.Red, 14)

    val y1 = CardFactory(Suit.Yellow, 1)
    val y2 = CardFactory(Suit.Yellow, 2)
    val y14 = CardFactory(Suit.Yellow, 14)

    val b1 = CardFactory(Suit.Black, 1)
    val b2 = CardFactory(Suit.Black, 2)
    val b14 = CardFactory(Suit.Black, 14)

    val e = CardFactory(Suit.Escape)
    val p = CardFactory(Suit.Pirate)
    val m = CardFactory(Suit.Mermaid)
    val sk = CardFactory(Suit.SkullKing)
    
    "nobody plays" should {
      "have no winner" in {
        Trick().winner should be(None)
      }
    }

    "playing any card" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val t0 = Trick()
      val t1 = t0.play(r1, p1)
      val t2 = t1.play(r2, p2)

      "have list of players" in {
        t0.players should be(List())
        t1.players should be(List(p1))
        t2.players should be(List(p1, p2))
      }

      "have list of cards" in {
        t0.cards should be(List())
        t1.cards should be(List(r1))
        t2.cards should be(List(r1, r2))
      }

      "have leading suit" in {
        t0.leadSuit should be(None)
        t1.leadSuit should be(Suit.Red)
        t2.leadSuit should be(Suit.Red)
      }

      "have a toString" in {
        t0.toString should be("Trick: ")
        t1.toString should be(s"Trick: ${p1.name}: ${r1.toString}")
        t2.toString should be(s"Trick: ${p1.name}: ${r1.toString}; ${p2.name}: ${r2.toString}")
      }
    }

    "playing only standard suits" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val trick = Trick().play(r1, p1)

      "be won by highest card of leading suit" in {
        trick.play(r2, p2).winner should be(Some(p2))
        trick.play(y14, p2).winner should be(Some(p1))
      }
    }

    "playing trump cards" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val p3 = Player("p3")
      val trick = Trick().play(r1, p1)

      "be won by highest trump card" in {
        trick.play(b1, p2).winner should be(Some(p2))
        trick.play(r14, p2).play(b1, p3).winner should be(Some(p3))
        trick.play(b1, p2).play(b2, p3).winner should be(Some(p3))
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
        trick.play(sk, p3).winner should be(Some(p3))
        trick.play(p, p4).play(sk, p3).winner should be(Some(p3))
      }
      "be won by pirate" in {
        trick.play(p, p3).winner should be(Some(p3))
        trick.play(p, p3).play(m, p4).winner should be(Some(p3))
        trick.play(p, p3).play(p, p4).winner should be(Some(p3))
      }
      "be won by mermaid" in {
        trick.play(m, p3).winner should be(Some(p3))
        trick.play(m, p3).play(sk, p4).winner should be(Some(p3))
        trick.play(m, p3).play(sk, p4).play(p, p5).winner should be(Some(p3))
        trick.play(m, p3).play(m, p4).winner should be(Some(p3))
      }
    }

    "playing escape cards" should {
      val p1 = Player("p1")
      val p2 = Player("p2")
      val escapeTrick = Trick().play(e, p1)

      "be won by escape" in {
        escapeTrick.play(e, p2).winner should be(Some(p1))
      }
      "have no leading suit if no non-escape card is present" in {
        escapeTrick.leadSuit should be(None)
        escapeTrick.play(e, p2).leadSuit should be(None)
        escapeTrick.play(m, p2).leadSuit should be(None)
      }
      "have first non-escape card as leading suit" in {
        val p1 = Player("p1")
        val t = escapeTrick.play(r1, p1)
        t.winner should be(Some(p1))
        t.leadSuit should be(Suit.Red)
      }
    }

    "calculating bonus points" should {
      "give 10 points for each standard suit 14 card" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        Trick().play(r14, p1).calculateBonusPoints should be(10)
        Trick().play(r14, p1).play(y14, p2).calculateBonusPoints should be(20)
      }
      "give 20 points for trump 14 card" in {
        val p1 = Player("p1")
        Trick().play(b14, p1).calculateBonusPoints should be(20)
      }
      "give 40 points for skull king + mermaid" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        Trick().play(sk, p1).play(m, p2).calculateBonusPoints should be(40)
      }
      "give 30 points per pirate if skull king present" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        val p3 = Player("p3")
        Trick().play(sk, p1).play(p, p2).calculateBonusPoints should be(30)
        Trick().play(sk, p1).play(p, p2).play(p, p3).calculateBonusPoints should be(60)
      }
      "give 20 points per mermaid if at least one pirate present" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        val p3 = Player("p3")
        Trick().play(p, p1).play(m, p2).calculateBonusPoints should be(20)
        Trick().play(p, p1).play(m, p2).play(m, p3).calculateBonusPoints should be(40)
      }
      "only give mermaid bonus if mermaid + skull king + pirate present" in {
        val p1 = Player("p1")
        val p2 = Player("p2")
        val p3 = Player("p3")
        val p4 = Player("p4")
        Trick().play(p, p1).play(m, p2).play(sk, p3).calculateBonusPoints should be(40)
        Trick().play(p, p1).play(m, p2).play(sk, p3).play(m, p4).calculateBonusPoints should be(40)
      }
    }
  }
}
