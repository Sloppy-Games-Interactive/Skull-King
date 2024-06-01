package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.util.{ObservableEvent, Observer, Prompter, PromptStrategy}
import de.htwg.se.skullking.util.TuiKeys

class Tui(controller: Controller) extends Observer {
  controller.add(this)

  println("Welcome to Skull King!")
  TuiKeys.values.foreach { key =>
    println(s"${key.productPrefix}, Key: ${key.key}")
  }

  val prompter = new Prompter(this, PromptStrategy.TUI)

  private def printTable(table: List[Seq[Any]]|Seq[Seq[Any]]): Unit = table.foreach { row =>
    println(row.map(_.toString).mkString(" | "))
  }

  def printStatusScreen(): Unit = {
    val players = controller.state.players
    val round = controller.state.round
    val phase = controller.state.phase
    val currentTrick = controller.state.activeTrick

    // print players in table format, columns for player name, prediction, score, and hand
    val playerTable = Seq("Name", "Prediction", "Score", "Hand", "Active") +: players.map { player =>
      Seq(player.name, player.prediction.getOrElse("-"), player.score, player.hand, if (player.active) "X" else "")
    }

    printTable(playerTable)

    println("----------------------------------")
    println()

    val statusTable = Seq(
      Seq("Round", "Phase", "Trick"),
      Seq(round, phase, currentTrick.getOrElse("-"))
    )

    printTable(statusTable)

    println("----------------------------------")
    println()
  }
  
  override def update(e: ObservableEvent): Unit = {
    e match {
      case ControllerEvents.Quit => {
        println("Goodbye!")
        System.exit(0)
      }
      case ControllerEvents.PromptPlayerLimit => {
        val limit = prompter.readPlayerLimit
        controller.setPlayerLimit(limit)
      }
      case ControllerEvents.PromptPlayerName => {
        val name = prompter.readPlayerName
        controller.addPlayer(name)
      }
      case ControllerEvents.PromptPrediction => {
        controller.state.activePlayer match {
          case Some(player) => {
            val prediction = prompter.readPlayerPrediction(player, controller.state.round)
            controller.setPrediction(player, prediction)
          }
          case None => println("No active player.")
        }
      }
      case ControllerEvents.PromptCardPlay => {
        controller.state.activePlayer match {
          case Some(player) => {
            val cardIndex = prompter.readPlayCard(player)
            controller.playCard(player, cardIndex)
          }
          case None => println("No active player.")
        }
      }
      case _ => printStatusScreen()
    }
  }

  def processInputLine(input: String): Unit = {
    input match {
      case TuiKeys.Quit.key => controller.quit
      case TuiKeys.Undo.key => controller.undo
      case TuiKeys.Redo.key => controller.redo
      case TuiKeys.NewGame.key => controller.newGame
      case _ =>  println("Invalid input.")
    }
  }
}
