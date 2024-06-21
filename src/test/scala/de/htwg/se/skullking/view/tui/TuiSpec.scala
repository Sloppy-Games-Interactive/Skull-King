package de.htwg.se.skullking.view.tui

import de.htwg.se.skullking.controller.ControllerComponent.BaseControllerImpl.Controller
import de.htwg.se.skullking.controller.ControllerComponent.ControllerEvents
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.Phase
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec {
  "A Tui" when {
    val initialGameState = GameState(
      phase = Phase.PrepareGame,
      players = List(),
      playerLimit = 2
    )

    "using :undo" should {
      "undo the last step" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        controller.state.players.length should be(0)
        controller.addPlayer("foo")
        controller.state.players.length should be(1)
        tui.processInputLine(":undo")
        controller.state.players.length should be(0)
      }
    }

    "using :redo" should {
      "redo the last undone step" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        controller.state.players.length should be(0)
        controller.addPlayer("foo")
        controller.state.players.length should be(1)
        tui.processInputLine(":undo")
        controller.state.players.length should be(0)
        tui.processInputLine(":redo")
        controller.state.players.length should be(1)
      }
    }

    "using :new game" should {
      "start new game" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        controller.state.playerLimit should be(2)
        controller.state.players.length should be(0)
        controller.addPlayer("foo")
        controller.state.players.length should be(1)
        tui.processInputLine(":new game")
        controller.state.playerLimit should be(0)
        controller.state.players.length should be(0)
      }
    }

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

    "typing an unknown command" should {
      "print input error" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        val out = new java.io.ByteArrayOutputStream()
        Console.withOut(out) {
          tui.processInputLine(":unknown command")
        }

        out.toString() should include("Invalid input.")
      }
    }

    "receiving controller events" should {
      "update promptState for PlayerLimitEvent" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        tui.update(ControllerEvents.PromptPlayerLimit)
        tui.promptState should be(PromptState.PlayerLimit)
      }

      "update promptState for PlayerNameEvent" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        tui.update(ControllerEvents.PromptPlayerName)
        tui.promptState should be(PromptState.PlayerName)
      }

      "update promptState for PredictionEvent" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)
        controller.addPlayer("Alice")
        controller.addPlayer("Bob")

        tui.update(ControllerEvents.PromptPrediction)
        tui.promptState should be(PromptState.Prediction)
      }

      "print error for no active players in PredictionEvent" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        val out = new java.io.ByteArrayOutputStream()
        Console.withOut(out) {
          tui.update(ControllerEvents.PromptPrediction)
        }

        out.toString() should include("No active player.")
      }

      "update promptState for CardPlayEvent" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)
        controller.addPlayer("Alice")
        controller.addPlayer("Bob")
        controller.setPrediction(controller.state.players.head, 1)
        controller.setPrediction(controller.state.players(1), 1)

        tui.update(ControllerEvents.PromptCardPlay)
        tui.promptState should be(PromptState.CardPlay)
      }

      "print error for no active players in CardPlayEvent" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        val out = new java.io.ByteArrayOutputStream()
        Console.withOut(out) {
          tui.update(ControllerEvents.PromptCardPlay)
        }

        out.toString() should include("No active player.")
      }
    }

    "parsing input" should {
      "parse player limit" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        tui.update(ControllerEvents.PromptPlayerLimit)
        tui.promptState should be(PromptState.PlayerLimit)
        tui.processInputLine("2")
        controller.state.playerLimit should be(2)
      }

      "re-prompt for player limit on invalid input" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        tui.update(ControllerEvents.PromptPlayerLimit)
        tui.promptState should be(PromptState.PlayerLimit)
        tui.processInputLine("invalid")
        tui.promptState should be(PromptState.PlayerLimit)
      }

      "parse player name" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        tui.update(ControllerEvents.PromptPlayerName)
        tui.promptState should be(PromptState.PlayerName)
        tui.processInputLine("Alice")
        controller.state.players.head.name should be("Alice")
      }

      "re-prompt for player name on invalid input" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)

        tui.update(ControllerEvents.PromptPlayerName)
        tui.promptState should be(PromptState.PlayerName)
        tui.processInputLine("")
        tui.promptState should be(PromptState.PlayerName)
      }

      "parse prediction" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)
        controller.addPlayer("Alice")
        controller.addPlayer("Bob")

        tui.update(ControllerEvents.PromptPrediction)
        tui.promptState should be(PromptState.Prediction)
        tui.processInputLine("1")
        controller.state.players.head.prediction should be(Some(1))
      }

      "re-prompt for prediction on invalid input" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)
        controller.addPlayer("Alice")
        controller.addPlayer("Bob")

        tui.update(ControllerEvents.PromptPrediction)
        tui.promptState should be(PromptState.Prediction)
        tui.processInputLine("invalid")
        tui.promptState should be(PromptState.Prediction)
      }

      "parse card play" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)
        controller.addPlayer("Alice")
        controller.addPlayer("Bob")
        controller.setPrediction(controller.state.players.head, 1)
        controller.setPrediction(controller.state.players(1), 1)

        tui.update(ControllerEvents.PromptCardPlay)
        tui.promptState should be(PromptState.CardPlay)
        tui.processInputLine("1")
        controller.state.players.head.hand.count should be(0)
      }

      "re-prompt for card play on invalid input" in {
        val controller = Controller(initialGameState)
        val tui: Tui = Tui(controller)
        controller.addPlayer("Alice")
        controller.addPlayer("Bob")
        controller.setPrediction(controller.state.players.head, 1)
        controller.setPrediction(controller.state.players(1), 1)

        tui.update(ControllerEvents.PromptCardPlay)
        tui.promptState should be(PromptState.CardPlay)
        tui.processInputLine("invalid")
        tui.promptState should be(PromptState.CardPlay)
      }
    }
  }
}