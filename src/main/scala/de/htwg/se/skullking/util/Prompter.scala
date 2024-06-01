package de.htwg.se.skullking.util

import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.PromptStrategy.TUI
import de.htwg.se.skullking.view.Tui

import scala.io.StdIn.readLine
import scala.util.{Success, Try}

enum PromptStrategy {
  case TUI
}

class Prompter(tui: Tui, var strategy: PromptStrategy = PromptStrategy.TUI) {
  def setStrategy(strategy: PromptStrategy): Unit = {
    this.strategy = strategy
  }
  
  private def readLineTui(): Option[String] = {
    val input = readLine()
    
    TuiKeys.values.find(_.key == input) match {
      case Some(key) => {
        tui.processInputLine(input)
        None
      }
      case None => Some(input)
    }
  }

  def readPlayerLimit: Int = {
    strategy match {
      case PromptStrategy.TUI => readPlayerLimitTui()
    }
  }

  private def readPlayerLimitTui(): Int = {
    val playerLimit: Option[Int] = LazyList.continually {
      println("Enter player count:")
      val tryPlayerLimit = Try(readLineTui().getOrElse("").toInt)

      tryPlayerLimit match {
        case Success(playerLimit) if playerLimit >= 2 && playerLimit <= 9 => Some(playerLimit)
        case _ => {
          println("Player count must be a number between 2 and 9.")
          None
        }
      }
    }.find(_.isDefined).flatten

    playerLimit.get
  }
  
  def readPlayCard(player: Player): Int = {
    strategy match
      case PromptStrategy.TUI => readPlayCardTui(player)
  }
  
  private def readPlayCardTui(player: Player): Int = {
    val cardIndexOption: Option[Int] = LazyList.continually {
      println(s" ${player.name} play your card: ")
      println(player.hand)
      val tryPrediction = Try(readLineTui().getOrElse("").toInt)

      tryPrediction match {
        case Success(cardIndex) if cardIndex >= 1 && cardIndex <= player.hand.count => Some(cardIndex)
        case _ => {
          println(s"Card index must be a number between 1 and ${player.hand.count}.")
          None
        }
      }
    }.find(_.isDefined).flatten

    val oneIndex = cardIndexOption.get
    // return zero-indexed card index, one-indexed card index is just for user convenience
    oneIndex - 1
  }
  
  def readPlayerName: String = {
    strategy match {
      case PromptStrategy.TUI => readPlayerNameTui()
    }
  }

  private def readPlayerNameTui(): String = {
    val name: Option[String] = LazyList.continually {
      println("Enter player name:")
      val tryName = Try(readLineTui().getOrElse(""))

      tryName match {
        case Success(name) if name.nonEmpty => Some(name)
        case _ => {
          println("Player name must not be empty.")
          None
        }
      }
    }.find(_.isDefined).flatten
    
    name.get
  }
  
  def readPlayerPrediction(player: Player, round: Int): Int = {
    strategy match {
      case PromptStrategy.TUI => readPlayerPredictionTui(player, round)
    }
  }
  
  private def readPlayerPredictionTui(player: Player, round: Int): Int = {
    val prediction: Option[Int] = LazyList.continually {
      println(s"Enter prediction for ${player.name}: ")
      val tryPrediction = Try(readLineTui().getOrElse("").toInt)

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
