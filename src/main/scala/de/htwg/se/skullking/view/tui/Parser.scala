package de.htwg.se.skullking.view.tui

import de.htwg.se.skullking.model.card.Card
import de.htwg.se.skullking.model.player.Player

import scala.util.{Success, Try}

class Parser {
  def parsePlayerLimit(input: String): Option[Int] = {
    val tryPlayerLimit = Try(input.toInt)

    tryPlayerLimit match {
      case Success(playerLimit) if playerLimit >= 2 && playerLimit <= 9 => Some(playerLimit)
      case _ => {
        println("Player count must be a number between 2 and 9.")
        None
      }
    }
  }

  def parsePlayerName(input: String): Option[String] = {
    val tryName = Try(input.trim)

    tryName match {
      case Success(name) if name.nonEmpty => Some(name)
      case _ => {
        println("Player name must not be empty.")
        None
      }
    }
  }

  def parsePrediction(input: String, round: Int): Option[Int] = {
    val tryPrediction = Try(input.toInt)

    tryPrediction match {
      case Success(prediction) if prediction >= 0 && prediction <= round => Some(prediction)
      case _ => {
        println(s"Prediction must be a number between 0 and ${round}.")
        None
      }
    }
  }

  def parseCardPlay(input: String, player: Player): Option[Card] = {
    val tryPrediction = Try(input.toInt)

    val oneIndexed = tryPrediction match {
      case Success(cardIndex) if cardIndex >= 1 && cardIndex <= player.hand.count => Some(cardIndex)
      case _ => {
        println(s"Card index must be a number between 1 and ${player.hand.count}.")
        None
      }
    }

    // return zero-indexed card index, one-indexed card index is just for user convenience
    val zeroIndexed = oneIndexed.map(_ - 1)
    zeroIndexed match {
      case Some(idx) if player.hand.cards.isDefinedAt(idx) => Some(player.hand.cards(idx))
      case _ => None
    }
  }
}
