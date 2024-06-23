package de.htwg.se.skullking.model.FileIOComponent.FileIOXmlImp

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.IGameState

import java.io.{File, PrintWriter}

class FileIO extends IFileIO {
  override def load: IGameState = {
    var gameState: IGameState = null
    val file = scala.xml.XML.loadFile("skullking.xml")
    val roundAttr = (file \\ "GameState" \ "@round")
    val round = roundAttr.text.toInt

    println("load")
    GameState()
  }

  override def save(gameState: IGameState): Unit = saveString(gameState)

  def saveXml(gameState: IGameState): Unit = {
    scala.xml.XML.save("skullking.xml", gameStateToXml(gameState), "UTF-8")
  }

  private def saveString(gameState: IGameState): String = {
    val pw = new PrintWriter(new File("skullking.xml"))
    val prettyPrinter = new scala.xml.PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStateToXml(gameState))
    pw.write(xml)
    pw.close()
    xml
  }

  private def gameStateToXml(gameState: IGameState): scala.xml.Elem = {
    <GameState phase={gameState.phase.toString}
               round={gameState.round.toString}
               playerLimit={gameState.playerLimit.toString}
               roundLimit={gameState.roundLimit.toString}
    >
      {gameState.players.map(playerToXml)}

    </GameState>
  }

  private def playerToXml(player: IPlayer): scala.xml.Elem = {
    <Player>
      <name>{player.name}</name>
      <score>{player.score.toString}</score>
      {handToXml(player.hand.cards)}
      <prediction>{player.prediction.getOrElse(0).toString}</prediction>
      <active>{player.active.toString}</active>
    </Player>
  }

  private def handToXml(hand: List[ICard]): scala.xml.Elem = {
    <Hand>
      {hand.map(card => {cardToXml(card)})}
    </Hand>
  }

  private def cardToXml(card: ICard): scala.xml.Elem = {
    <Card>
      <suit>{card.suit}</suit>
      <rank>{card match{
        case standardCard: IStandardCard =>
          standardCard.value
        case _ =>
      }
          }
          </rank>
    </Card>
  }
}
