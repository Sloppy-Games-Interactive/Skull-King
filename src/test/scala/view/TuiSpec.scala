import model.{Player, GameState}
import controller.Controller
import view.Tui
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