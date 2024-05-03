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
      case "r" => controller.prepareRound
      case "d" => controller.dealCards
      case "yo ho ho" => controller.startTrick
      case _ =>  // TODO parse player playing card
    }
  }
}
