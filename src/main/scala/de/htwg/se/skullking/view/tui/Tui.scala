package de.htwg.se.skullking.view.tui

import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.util.TuiKeys

import scala.util.{Success, Try}

enum PromptState {
  case None
  case PlayerLimit
  case PlayerName
  case Prediction
  case CardPlay
  case NewGame
}

class Tui(controller: Controller) extends Observer {
  var promptState: PromptState = PromptState.None
  
  val prompter = new Prompter
  val parser = new Parser
  
  controller.add(this)

  println("Welcome to Skull King!")
  TuiKeys.values.foreach { key =>
    println(s"${key.productPrefix}, Key: ${key.key}")
  }

  private def printTable(table: List[Seq[Any]] | Seq[Seq[Any]]): Unit = table.foreach { row =>
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
        promptState = PromptState.PlayerLimit
        prompter.promptPlayerLimit
      }
      case ControllerEvents.PromptPlayerName => {
        promptState = PromptState.PlayerName
        prompter.promptPlayerName
      }
      case ControllerEvents.PromptPrediction => {
        controller.state.activePlayer match {
          case Some(player) => {
            promptState = PromptState.Prediction
            prompter.promptPrediction(player.name, controller.state.round)
          }
          case None => println("No active player.")
        }
      }
      case ControllerEvents.PromptCardPlay => {
        controller.state.activePlayer match {
          case Some(player) => {
            promptState = PromptState.CardPlay
            prompter.promptCardPlay(player)
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
      case _ => promptState match {
        case PromptState.PlayerLimit => parser.parsePlayerLimit(input) match {
          case Some(playerLimit) => controller.setPlayerLimit(playerLimit)
          case None => prompter.promptPlayerLimit
        }
        case PromptState.PlayerName => parser.parsePlayerName(input) match {
          case Some(playerName) => controller.addPlayer(playerName)
          case None => prompter.promptPlayerName
        }
        case PromptState.Prediction => parser.parsePrediction(input, controller.state.round) match {
          case Some(prediction) => controller.setPrediction(controller.state.activePlayer.get, prediction)
          case None => prompter.promptPrediction(controller.state.activePlayer.get.name, controller.state.round)
        }
        case PromptState.CardPlay => parser.parseCardPlay(input, controller.state.activePlayer.get) match {
          case Some(cardIndex) => controller.playCard(controller.state.activePlayer.get, cardIndex)
          case None => prompter.promptCardPlay(controller.state.activePlayer.get)
        }
        case _ => println("Invalid input.")
      }
    }
  }
}




