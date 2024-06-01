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

  val prompter = new Prompter(PromptStrategy.TUI)
  
  override def update(e: ObservableEvent): Unit = {
    e match {
      case ControllerEvents.Quit => println("Goodbye!")
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
      case _ => {
        println(controller.state.activePlayer)
        println(controller.state.players.length)
        println(controller.state.players)
        println(controller.state.phase)
      }
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
