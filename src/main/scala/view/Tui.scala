package view

import controller.Controller
import model.GameState
import util.Observer

import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Observer {

  controller.add(this)
  
  override def update: Unit = {
    print(controller.state.getStatusAsTable)
  }

  def processInputLine(input: String): Unit = {
    input match {
      case "q" => 
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
