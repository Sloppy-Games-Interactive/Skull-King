package de.htwg.se.skullking.model.FileIOComponent.FileIOJsonImpl

import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.IGameState

import scala.io.Source
import play.api.libs.json.Json


class FileIO extends IFileIO {
  override def load: IGameState = {

    val source: String = Source.fromFile("skullking.json").getLines.mkString

    println("load")
    GameState()
  }

  override def save(gameState: IGameState): Unit = {
    println("save")
  }

}
