package de.htwg.se.skullking.model.FileIOComponent.FileIOJsonImpl

import de.htwg.se.skullking.model.CardComponent.{ICard, IStandardCard}
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.IGameState

import scala.io.Source
import play.api.libs.json.{JsObject, Json}

import java.io.{File, PrintWriter}


class FileIO extends IFileIO {

  override def load: IGameState = {
    var gameState: IGameState = null
    val source: String = Source.fromFile("skullking.json").getLines.mkString
    val json = Json.parse(source)
    val round = (json \ "round").as[Int]
    val phase = (json \ "phase").as[Int]
    val playerLimit = (json \ "playerLimit").as[Int]
    val roundLimit = (json \ "roundLimit").as[Int]

    println("load")
    GameState()
  }

  override def save(gameState: IGameState): Unit = {
    println("save")

    val pw = new PrintWriter(new File("skullking.json"))
    pw.write(Json.prettyPrint(gameStateToJson(gameState)))
    pw.close()
  }

  private def gameStateToJson(gameState: IGameState) = {
    Json.obj(
      "round" -> gameState.round,
      "phase" -> gameState.phase.toString,
      "playerLimit" -> gameState.playerLimit,
      "roundLimit" -> gameState.roundLimit,
      "players" -> gameState.players.map(playerToJson)
    )
  }

  private def playerToJson(player: IPlayer) = {
    Json.obj(
      "name" -> player.name,
      "score" -> player.score,
      "hand" -> player.hand.cards.map(cardToJson),
      "prediction" -> player.prediction,
      "active" -> player.active
    )
  }

  private def cardToJson(card: ICard): JsObject = {
    Json.obj(
      "suit" -> card.suit.toString,
      "value" -> {
        card match {
          case standardCard: IStandardCard => standardCard.value
          case _ => 0
        }
      }
    )
  }

}
