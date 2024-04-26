import model.{Player, GameState}
import controller.Controller
import view.Tui
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class TuiSpec extends AnyWordSpec {
  val tui: Tui = Tui(Controller())

  "n" should {
    "start new game" in {
      val initialState = GameState(List(), 5)
      val state = tui.processInputLine("n", initialState)

      initialState.round should be(5)
      state.round should be(0)
    }
  }
  "p" should {
    "add player" in {
        val input = ByteArrayInputStream("foo\n".getBytes(StandardCharsets.UTF_8))
        val initialState = GameState()
        val state = Console.withIn(input) {
          tui.processInputLine("p", initialState)
        }

        state.players should have length 1
        state.players.head.name should be("foo")
    }
  }
  "r" should {
    "prepare round" in {
      val initialState = GameState(List(), 5)
      val state = tui.processInputLine("r", initialState)

      initialState.round should be(5)
      state.round should be(6)
    }
  }
  "d" should {
    "deal cards" in {
      val initialState = GameState(List(Player("foo")), 5).startNewRound
      val state = tui.processInputLine("d", initialState)

      initialState.players.head.hand.count should be(0)
      state.players.head.hand.count should be(6)
    }
  }
  "yo ho ho" should {
    "start a new trick" in {
      val initialState = GameState(List(), 5)
      val state = tui.processInputLine("yo ho ho", initialState)
      assert(true)
    }
  }
  "q" should {
    "quit" in { // TODO
      val initialState = GameState(List(), 5)
      val state = tui.processInputLine("q", initialState)
      assert(true)
    }
  }
  "_" should {
    "parse player input" in { // TODO
      val initialState = GameState(List(), 5)
      val state = tui.processInputLine("TODO", initialState)
      assert(true)
    }
  }
}