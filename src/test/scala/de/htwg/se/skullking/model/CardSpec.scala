package de.htwg.se.skullking.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

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
      val card = Card(SpecialSuit.Joker, 0)
      card.toString should be("🤡 0")
    }

    "be identifiable as Trump" in {
      val card = Card(Suit.Black, 1)
      val r1 = Card(Suit.Red, 1)
      val p = Card(SpecialSuit.Pirate, 0)
      
      card.isTrump should be(true)
      r1.isTrump should be(false)
      p.isTrump should be(false)
    }

    "be identifiable as Special" in {
      val p = Card(SpecialSuit.Pirate, 0)
      val r1 = Card(Suit.Red, 1)
      val b1 = Card(Suit.Black, 1)
      
      p.isSpecial should be(true)
      r1.isSpecial should be(false)
      b1.isSpecial should be(false)
    }
  }
}