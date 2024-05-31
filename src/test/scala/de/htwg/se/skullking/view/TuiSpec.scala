package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.state.{GameState, Phase}
import de.htwg.se.skullking.model.player.Player
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class TuiSpec extends AnyWordSpec {
  // TODO: fix tui tests whenever regular tui commands work again
//  "z" should {
//    "undo" in {
//      val controller = Controller(GameState(Phase.PrepareGame, List(Player("foo")), 5))
//      val tui: Tui = Tui(controller)
//
//      controller.state.round should be(5)
//      controller.prepareRound
//      controller.state.round should be(6)
//      tui.processInputLine("z")
//      controller.state.round should be(5)
//    }
//  }
//
//  "y" should {
//    "redo" in {
//      val controller = Controller(GameState(Phase.PrepareGame, List(Player("foo")), 5))
//      val tui: Tui = Tui(controller)
//
//      controller.state.round should be(5)
//      controller.prepareRound
//      controller.state.round should be(6)
//      tui.processInputLine("z")
//      controller.state.round should be(5)
//      tui.processInputLine("y")
//      controller.state.round should be(6)
//    }
//  }
//
//  "n" should {
//    "start new game" in {
//      val controller = Controller(GameState(Phase.PrepareGame, List(Player("foo")), 5))
//      val tui: Tui = Tui(controller)
//
//      controller.state.round should be(5)
//      tui.processInputLine("n")
//      controller.state.round should be(0)
//    }
//  }
//
//  "q" should {
//    "quit" in {
//      val controller = Controller(GameState(Phase.PrepareGame, List(Player("foo")), 5))
//      val tui: Tui = Tui(controller)
//
//      val out = new java.io.ByteArrayOutputStream()
//      Console.withOut(out) {
//        tui.processInputLine("q")
//      }
//
//      out.toString() should include("Goodbye")
//    }
//  }
//
//  "_" should {
//    val controller = Controller(GameState(Phase.PrepareGame, List(Player("foo")), 5))
//    val tui: Tui = Tui(controller)
//
//    "parse player input" in { // TODO
//      val initialState = GameState(Phase.PrepareGame, List(), 5)
//      val state = tui.processInputLine("TODO")
//      assert(true)
//    }
//  }
}