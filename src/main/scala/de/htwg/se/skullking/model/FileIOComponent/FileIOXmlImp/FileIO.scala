package de.htwg.se.skullking.model.FileIOComponent.FileIOXmlImp

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.{ICard, ICardFactory, IStandardCard, Suit}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.Deck
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.{IHand, IPlayer}
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, PlayerFactory}
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.{GameStateDeserializer, IGameState, Phase}
import de.htwg.se.skullking.model.trick.TrickComponent.TrickBaseImpl.Trick

import java.io.{File, PrintWriter}
import scala.xml.Elem

class FileIO extends IFileIO {

  override def load: IGameState = {
    val file = scala.xml.XML.loadFile("skullking.xml")
    if (file == null) {
      println("File not found")
      return null
    }
    GameStateDeserializer.fromXml((file \\ "GameState").head.asInstanceOf[Elem])
  }
  
  override def save(gameState: IGameState): Unit = saveString(gameState)
  
  private def saveString(gameState: IGameState): String = {
    val pw = new PrintWriter(new File("skullking.xml"))
    val prettyPrinter = new scala.xml.PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameState.toXml)
    pw.write(xml)
    pw.close()
    xml
  }

}
