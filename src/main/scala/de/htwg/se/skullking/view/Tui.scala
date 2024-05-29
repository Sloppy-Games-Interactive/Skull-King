package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.util.{ObservableEvent, Observer}

import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  
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
      case "p" => {
        println("Enter player name: ")
        val name = readLine()
        controller.addPlayer(name)
      }
      // predict the tricks for each player
      case "pt" => {
        for (player <- controller.state.players) {
          var validPrediction = false
          while (!validPrediction) {
            println(s"Enter prediction for ${player.name}: ")
            val prediction = readLine().toInt
            if (prediction < 0 || prediction > controller.state.round) {
              println("Prediction must be greater or equal to 0 and less or equal to round number.")
            } else {
              controller.setPrediction(player, prediction)
              validPrediction = true
            }
          }
        }
      }
      case "r" => controller.prepareRound
      case "d" => controller.dealCards
      case "yo ho ho" => controller.startTrick
      case _ =>  // TODO parse player playing card
    }
  }
}
