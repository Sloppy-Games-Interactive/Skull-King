package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.model.player.Player
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class TuiSpec extends AnyWordSpec {

  "n" should {
    "start new game" in {
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      controller.state.round should be(5)
      tui.processInputLine("n")
      controller.state.round should be(0)
    }
  }

  "p" should {
    "add player" in {
      val controller = Controller(GameState(List(), 5))
      val tui: Tui = Tui(controller)

        val input = ByteArrayInputStream("foo\n".getBytes(StandardCharsets.UTF_8))
        Console.withIn(input) {
          tui.processInputLine("p")
        }

        controller.state.players should have length 1
        controller.state.players.head.name should be("foo")
    }
  }

  "r" should {
    "prepare round" in {
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      controller.state.round should be(5)
      tui.processInputLine("r")
      controller.state.round should be(6)
    }
  }

  "d" should {
    "deal cards" in {
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      controller.prepareRound
      controller.state.players.head.hand.count should be(0)
      tui.processInputLine("d")
      controller.state.players.head.hand.count should be(6)
    }
  }

  "pt" should {
    "set prediction" in {
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      controller.prepareRound
      controller.state.players.head.prediction should be(0)
      val input = ByteArrayInputStream("3\n".getBytes(StandardCharsets.UTF_8))
      Console.withIn(input) {
        tui.processInputLine("pt")
      }
      controller.state.players.head.prediction should be(3)
    }

    "set prediction with to great round (invalid) input" in {
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      controller.prepareRound
      controller.state.players.head.prediction should be(0)
      // 7 because round is 5 + prepareRound increments round by 1
      val input = ByteArrayInputStream("7\n3\n".getBytes(StandardCharsets.UTF_8))
      Console.withIn(input) {
        tui.processInputLine("pt")
      }
      controller.state.players.head.prediction should be(3)
    }

    "set prediction with to small round (invalid) input" in {
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      controller.prepareRound
      controller.state.players.head.prediction should be(0)
      val input = ByteArrayInputStream("-1\n3\n".getBytes(StandardCharsets.UTF_8))
      Console.withIn(input) {
        tui.processInputLine("pt")
      }
      controller.state.players.head.prediction should be(3)
    }
  }

  "yo ho ho" should {
    "start a new trick" in { // TODO
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      tui.processInputLine("yo ho ho")
      assert(true)
    }
  }

  "q" should {
    "quit" in {
      val controller = Controller(GameState(List(Player("foo")), 5))
      val tui: Tui = Tui(controller)

      val out = new java.io.ByteArrayOutputStream()
      Console.withOut(out) {
        tui.processInputLine("q")
      }

      out.toString() should include("Goodbye")
    }
  }

  "_" should {
    val controller = Controller(GameState(List(Player("foo")), 5))
    val tui: Tui = Tui(controller)

    "parse player input" in { // TODO
      val initialState = GameState(List(), 5)
      val state = tui.processInputLine("TODO")
      assert(true)
    }
  }

}