package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.util.{ObservableEvent, Observer, Prompter, PromptStrategy}

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}

class Tui(controller: Controller) extends Observer {
  controller.add(this)

  val prompter = new Prompter(PromptStrategy.TUI)
  
  override def update(e: ObservableEvent): Unit = {
    e match {
      case ControllerEvents.Quit => println("Goodbye!")
      case _ => println(controller.state.getStatusAsTable)
    }
  }

  def processInputLine(input: String): Unit = {
    input match {
      case "q" => controller.quit
      case "n" => controller.newGame
      case "z" => controller.undo
      case "y" => controller.redo
      case "p" => {
        val name = prompter.readPlayerName
        controller.addPlayer(name)
      }
      // predict the tricks for each player
      case "pt" => {
        controller.state.players.foreach(player => {
          val prediction = prompter.readPlayerPrediction(player, controller.state.round)
          controller.setPrediction(player, prediction)
        })
      }
      case "r" => controller.prepareRound
      case "d" => controller.dealCards
      case "yo ho ho" => controller.startTrick
      case _ =>  println("Invalid input.")
    }
  }
}
