package de.htwg.se.skullking.util

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.card.{Card, Suit}
import de.htwg.se.skullking.model.player.{Hand, Player}
import de.htwg.se.skullking.view.Tui
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class PrompterSpec extends AnyWordSpec {
  "A Prompter" when {
    val tui = Tui(Controller(null))
    val prompter = new Prompter(tui)

    "reading player limit" should {
      "return the correct limit" in {
        val input = ByteArrayInputStream("2\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayerLimit should be(2)
        }
      }

      "prompt again for invalid input" in {
        val input = ByteArrayInputStream("1\n-1\n100\nfoo\n2\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayerLimit should be(2)
        }
      }
    }

    "reading player name" should {
      "return the correct name" in {
        val input = ByteArrayInputStream("foo\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayerName should be("foo")
        }
      }

      "prompt again for empty input" in {
        val input = ByteArrayInputStream("\nfoo\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayerName should be("foo")
        }
      }
    }

    "reading player card" should {
      val player = Player("Alice", Hand(List(Card(Suit.Red, 1))))

      "return the correct card index" in {
        val input = ByteArrayInputStream("1\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayCard(player) should be(0)
        }
      }

      "prompt again for invalid input" in {
        val input = ByteArrayInputStream("0\n5\n-1\nfoo\n1\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayCard(player) should be(0)
        }
      }
    }

    "reading player prediction" should {
      val player = Player("Alice")

      "return the correct prediction" in {
        val input = ByteArrayInputStream("1\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayerPrediction(player, 1) should be(1)
        }
      }

      "prompt again for invalid input" in {
        val input = ByteArrayInputStream("-1\n2\nfoo\n1\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          prompter.readPlayerPrediction(player, 1) should be(1)
        }
      }
    }

    "setting strategy" should {
      "set the correct strategy" in {
        prompter.setStrategy(PromptStrategy.TUI)
        prompter.strategy should be(PromptStrategy.TUI)
      }
    }
  }
}
