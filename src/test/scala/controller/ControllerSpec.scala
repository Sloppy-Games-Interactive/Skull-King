package controller

import controller.Controller
import model.{GameState, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import view.Tui

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class ControllerSpec extends AnyWordSpec {
  val controller = Controller()

  "Controller" should {
    "start new game" in {
      val state = controller.newGame

      state.round should be(0)
    }
    "add player" in {
      val initialState = GameState(List(), 5)
      val state = controller.addPlayer(initialState, "foo")

      state.players should have length 1
      state.players.head.name should be("foo")
    }
    "prepare round" in {
      val initialState = GameState(List(), 5)
      val state = controller.prepareRound(initialState)

      initialState.round should be(5)
      state.round should be(6)
    }
    "deal cards" in {
      val initialState = GameState(List(Player("foo")), 5).startNewRound
      val state = controller.dealCards(initialState)

      initialState.players.head.hand.count should be(0)
      state.players.head.hand.count should be(6)
    }
    "start a new trick" in {
      val initialState = GameState(List(), 5)
      val state = controller.startTrick(initialState)
      assert(true)
    }
  }
}