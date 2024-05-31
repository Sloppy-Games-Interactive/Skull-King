package de.htwg.se.skullking.view

import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.util.{ObservableEvent, Observer, Prompter, PromptStrategy}
import de.htwg.se.skullking.util.TuiKeys

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}

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
      case _ => println(controller.state.getStatusAsTable)
    }
  }

  def processInputLine(input: String): Unit = {
    input match {
      case TuiKeys.Quit.key => controller.quit
      case TuiKeys.Undo.key => controller.undo
      case TuiKeys.Redo.key => controller.redo
      case TuiKeys.NewGame.key => controller.newGame
      case TuiKeys.AddPlayer.key => {
        val name = prompter.readPlayerName
        controller.addPlayer(name)
      }
      // predict the tricks for each player
      case TuiKeys.SetPrediction.key => {
        controller.state.players.foreach(player => {
          val prediction = prompter.readPlayerPrediction(player, controller.state.round)
          controller.setPrediction(player, prediction)
        })
      }
      case TuiKeys.PrepareRound.key => controller.prepareRound
      case TuiKeys.DealCards.key => controller.dealCards
      case TuiKeys.PlayCard.key => {
        controller.state.players.foreach(player => {
          if (player.hand.count > 0) {
            val cardIndex = prompter.readPlayCard(player)
            controller.playCard(player, cardIndex)
          } else {
            println(s"${player.name} has already played all cards.")
          }
        })
      }
      case TuiKeys.StartTrick.key => controller.startTrick
      case _ =>  println("Invalid input.")
    }
  }
}
