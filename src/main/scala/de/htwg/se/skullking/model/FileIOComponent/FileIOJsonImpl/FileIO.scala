package de.htwg.se.skullking.model.FileIOComponent.FileIOJsonImpl

import de.htwg.se.skullking.model.CardComponent.{ICard, IStandardCard}
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.{GameStateDeserializer, IGameState}

import scala.io.Source
import play.api.libs.json.{JsObject, Json}

import java.io.{File, PrintWriter}


class FileIO extends IFileIO {

  override def load: IGameState = {
    val source: String = Source.fromFile("skullking.json").getLines.mkString
    val json = Json.parse(source)

    GameStateDeserializer.fromJson(json.as[JsObject])
  }

  override def save(gameState: IGameState): Unit = {
    println("save")

    val pw = new PrintWriter(new File("skullking.json"))
    pw.write(Json.prettyPrint(gameState.toJson))
    pw.close()
  }


}
