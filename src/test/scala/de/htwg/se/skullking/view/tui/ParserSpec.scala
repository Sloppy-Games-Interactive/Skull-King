package de.htwg.se.skullking.view.tui

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.Suit
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ParserSpec extends AnyWordSpec {
  val parser = new Parser

  "A Parser" when {
    "parsing player limit" should {
      "return Some value for valid input" in {
        parser.parsePlayerLimit("5") shouldEqual Some(5)
      }

      "return None for invalid input" in {
        parser.parsePlayerLimit("10") shouldBe None
        parser.parsePlayerLimit("abc") shouldBe None
      }
    }

    "parsing player name" should {
      "return Some value for valid input" in {
        parser.parsePlayerName("Alice") shouldEqual Some("Alice")
      }

      "return None for invalid input" in {
        parser.parsePlayerName("") shouldBe None
      }
    }

    "parsing prediction" should {
      "return Some value for valid input" in {
        parser.parsePrediction("2", 3) shouldEqual Some(2)
      }

      "return None for invalid input" in {
        parser.parsePrediction("4", 3) shouldBe None
        parser.parsePrediction("abc", 3) shouldBe None
      }
    }

    "parsing card play" should {
      "return Some value for valid input" in {
        val player = Player("Bob", Hand(List(CardFactory()(Suit.Pirate), CardFactory()(Suit.Red, 2))))
        parser.parseCardPlay("1", player) shouldEqual Some(player.hand.cards.head)
      }

      "return None for invalid input" in {
        val player = Player("Bob", Hand(List(CardFactory()(Suit.Pirate), CardFactory()(Suit.Red, 2))))
        parser.parseCardPlay("3", player) shouldBe None
        parser.parseCardPlay("abc", player) shouldBe None
      }
    }
  }
}
