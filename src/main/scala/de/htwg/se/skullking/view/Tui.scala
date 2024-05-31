package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.util.{ObservableEvent, Observer, Prompter, PromptStrategy}

class Tui(controller: Controller) extends Observer {
  controller.add(this)

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
      case "q" => controller.quit
      case "n" => controller.newGame
      case "z" => controller.undo
      case "y" => controller.redo
      case _ =>  println("Invalid input.")
    }
  }
}
