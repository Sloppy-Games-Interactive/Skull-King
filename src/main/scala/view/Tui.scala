package view

import controller.Controller
import model.GameState
import scala.io.StdIn.readLine

class Tui(controller: Controller) {
  def processInputLine(input: String, state: GameState): GameState = {
    input match {
      case "q" => state
      case "n" => controller.newGame
      case "p" => {
        println("Enter player name: ")
        val name = readLine()
        controller.addPlayer(state, name)
      }
      case "r" => controller.prepareRound(state)
      case "d" => controller.dealCards(state)
      case "yo ho ho" => controller.startTrick(state)
      case _ => state // TODO parse player playing card
    }
  }
}
