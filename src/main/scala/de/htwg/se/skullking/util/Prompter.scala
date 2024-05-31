package de.htwg.se.skullking.util

import de.htwg.se.skullking.model.player.Player

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}

enum PromptStrategy {
  case TUI
}

class Prompter(var strategy: PromptStrategy = PromptStrategy.TUI) {
  def setStrategy(strategy: PromptStrategy): Unit = {
    this.strategy = strategy
  }
  
  def readPlayerName: String = {
    strategy match {
      case PromptStrategy.TUI => readPlayerNameTui()
    }
  }

  private def readPlayerNameTui(): String = {
    println("Enter player name:")
    readLine()
  }
  
  def readPlayerPrediction(player: Player, round: Int): Int = {
    strategy match {
      case PromptStrategy.TUI => readPlayerPredictionTui(player, round)
    }
  }
  
  private def readPlayerPredictionTui(player: Player, round: Int): Int = {
    val prediction: Option[Int] = LazyList.continually {
      println(s"Enter prediction for ${player.name}: ")
      val tryPrediction = Try(readLine().toInt)

      tryPrediction match {
        case Success(prediction) if prediction >= 0 && prediction <= round => Some(prediction)
        case _ => {
          println(s"Prediction must be a number between 0 and ${round}.")
          None
        }
      }
    }.find(_.isDefined).flatten
    
    prediction.get
  }
}
