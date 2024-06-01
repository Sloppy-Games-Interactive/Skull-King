package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.state.{GameState, Phase}
import de.htwg.se.skullking.model.player.Player
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class TuiSpec extends AnyWordSpec {
//  "A Tui" when {
//    val initialGameState = GameState(
//      phase = Phase.PrepareGame,
//      players = List(),
//      playerLimit = 2
//    )
//
//    "using :undo" should {
//      "undo the last step" in {
//        val controller = Controller(initialGameState)
//        val tui: Tui = Tui(controller)
//
//        controller.state.players.length should be(0)
//        controller.addPlayer("foo")
//        controller.state.players.length should be(1)
//        tui.processInputLine(":undo")
//        controller.state.players.length should be(0)
//      }
//    }
//
//    "using :redo" should {
//      "redo the last undone step" in {
//        val controller = Controller(initialGameState)
//        val tui: Tui = Tui(controller)
//
//        controller.state.players.length should be(0)
//        controller.addPlayer("foo")
//        controller.state.players.length should be(1)
//        tui.processInputLine(":undo")
//        controller.state.players.length should be(0)
//        tui.processInputLine(":redo")
//        controller.state.players.length should be(1)
//      }
//    }
//
//    "using :new game" should {
//      "start new game" in {
//        val controller = Controller(initialGameState)
//        val tui: Tui = Tui(controller)
//
//        controller.state.playerLimit should be(2)
//        controller.state.players.length should be(0)
//        controller.addPlayer("foo")
//        controller.state.players.length should be(1)
//        tui.processInputLine(":new game")
//        controller.state.playerLimit should be(0)
//        controller.state.players.length should be(0)
//      }
//    }
//
//    "using :quit" should {
//      "quit" in {
//        val controller = Controller(initialGameState)
//        val tui: Tui = Tui(controller)
//
//        val out = new java.io.ByteArrayOutputStream()
//        Console.withOut(out) {
//          tui.processInputLine(":quit")
//        }
//
//        out.toString() should include("Goodbye")
//      }
//    }
//
//    "typing an unknown command" should {
//      "print input error" in {
//        val controller = Controller(initialGameState)
//        val tui: Tui = Tui(controller)
//
//        val out = new java.io.ByteArrayOutputStream()
//        Console.withOut(out) {
//          tui.processInputLine(":unknown command")
//        }
//
//        out.toString() should include("Invalid input.")
//      }
//    }
//  }
}