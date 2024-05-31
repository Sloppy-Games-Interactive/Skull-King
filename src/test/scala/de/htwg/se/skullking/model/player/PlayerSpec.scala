package de.htwg.se.skullking.model.player

import de.htwg.se.skullking.model.card.{Card, Suit}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec {
  "Player" should {

    "have defaults" in {
      val p1 = Player("")
      p1.name should be("")
      p1.hand.count should be(0)
      p1.score should be(0)
      p1.prediction should be(None)
    }

    "have a name" in {
      val p1 = Player("p1", Hand(List()), 9, Some(0))
      p1.name should be("p1")
    }

    "have resettable hand" in {
      val cards = List(Card(Suit.Red, 1), Card(Suit.Blue, 2), Card(Suit.Red, 3))
      val p1 = Player("p1", Hand(cards), 9)
      val p1reset = p1.resetHand

      p1.hand.count should be(3)
      p1reset.hand.count should be(0)
      p1.score should be(9)
      p1reset.score should be(9)
      p1.name should be("p1")
      p1reset.name should be("p1")
    }
  }

  "Player" should {
    "have a score" in {
      val p1 = Player("p1", Hand(List()), 9)
      p1.score should be(9)
    }
  }

  "Player" should {
    "have a resettable prediction" in {
      val p1 = Player("p1", Hand(List()), 9, Some(0))
      val p1reset = p1.resetPrediction

      p1.prediction should be(Some(0))
      p1reset.prediction should be(None)
    }
  }
}
