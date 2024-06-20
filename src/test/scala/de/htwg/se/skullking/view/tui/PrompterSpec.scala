package de.htwg.se.skullking.view.tui

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.Suit
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PrompterSpec extends AnyWordSpec {
  "Prompter" should {
    "prompt for player count" in {
      val prompter = new Prompter
      val output = new java.io.ByteArrayOutputStream
      Console.withOut(output) {
        prompter.promptPlayerLimit
      }
      output.toString should include("Enter player count:")
    }

    "prompt for player name" in {
      val prompter = new Prompter
      val output = new java.io.ByteArrayOutputStream
      Console.withOut(output) {
        prompter.promptPlayerName
      }
      output.toString should include("Enter player name:")
    }

    "prompt for prediction" in {
      val prompter = new Prompter
      val output = new java.io.ByteArrayOutputStream
      Console.withOut(output) {
        prompter.promptPrediction("Alice", 1)
      }
      output.toString should include("Alice, enter your prediction for round 1:")
    }

    "prompt for card play" in {
      val prompter = new Prompter
      val player = Player("Bob", Hand(List(CardFactory(Suit.Pirate), CardFactory(Suit.Red, 2))))
      val output = new java.io.ByteArrayOutputStream
      Console.withOut(output) {
        prompter.promptCardPlay(player)
      }
      output.toString should include("Bob, play your card:")
      output.toString should include(Suit.Pirate.readable)
      output.toString should include(Suit.Red.readable)
    }
  }
}