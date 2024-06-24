package de.htwg.se.skullking.model.FileIOComponent.FileIOXmlImp

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.{ICard, ICardFactory, IStandardCard, Suit}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.Deck
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.{IHand, IPlayer}
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, PlayerFactory}
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.{IGameState, Phase}
import de.htwg.se.skullking.model.TrickComponent.TrickBaseImpl.Trick

import java.io.{File, PrintWriter}

class FileIO extends IFileIO {

  /////////////  load   //////////////  
  override def load: IGameState = {
    var gameState: IGameState = null
    val file = scala.xml.XML.loadFile("skullking.xml")
    if (file == null) {
      println("File not found")
      return null
    }
    val round = (file \\ "GameState" \ "@round").text.toInt
    val phase = (file \\ "GameState" \ "@phase").text
    val playerLimit = (file \\ "GameState" \ "@playerLimit").text.toInt
    val roundLimit = (file \\ "GameState" \ "@roundLimit").text.toInt
    val deck = new Deck((file \\ "Deck" \ "Card").map(xmlToHand(_)).toList)

    val players = (file \\ "Player").map(player => {
      xmlToPlayer(player)
    }).toList

    val tricks = (file \\ "Tricks" \ "Trick").map(trick => {
      val cards = (trick \ "Card").map(xmlToHand(_)).toList
      val players = (trick \ "Player").map(xmlToPlayer(_)).toList
      new Trick(cards.zip(players))
    }).toList
    
    println("load")
    GameState(
      round = round,
      phase = phase match {
        case "PrepareGame" => Phase.PrepareGame
        case "PrepareTricks" => Phase.PrepareTricks
        case "PlayTricks" => Phase.PlayTricks
        case "EndGame" => Phase.EndGame
      },
      playerLimit = playerLimit,
      roundLimit = roundLimit,
      players = players,
      deck = deck,
      tricks = tricks)
  }

  private def xmlToPlayer(player: scala.xml.NodeSeq): IPlayer = {
    val name = (player \ "name").text
    val score = (player \ "score").text.toInt
    val hand = new Hand((player \ "Hand" \ "Card").map(xmlToHand(_)).toList)
    val _prediction = (player \ "prediction").text.toInt
    val _active = (player \ "active").text.toBoolean
    PlayerFactory.create(name).setScore(score).setHand(hand).setPrediction(_prediction).setActive(_active)
  }

  private def xmlToHand(card: scala.xml.NodeSeq): ICard = {
      val suit = (card \ "suit").text
      val rank = (card \ "rank").text
      suit match {
        case "Joker" => CardFactory(Suit.Joker)
        case "Pirate" => CardFactory(Suit.Pirate)
        case "Mermaid" => CardFactory(Suit.Mermaid)
        case "SkullKing" => CardFactory(Suit.SkullKing)
        case "Escape" => CardFactory(Suit.Escape)
        case "Blue" => CardFactory(Suit.Blue, rank.toInt)
        case "Red" => CardFactory(Suit.Red, rank.toInt)
        case "Yellow" => CardFactory(Suit.Yellow, rank.toInt)
        case "Black" => CardFactory(Suit.Black, rank.toInt)
      }
  }
  
  
  
  /////////////   save   //////////////
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
    >{gameState.players.map(playerToXml)}
    <Deck>
      {gameState.deck.getCards.map(cardToXml) }
    </Deck>
      <Tricks>
        {gameState.tricks.map(trick => {
          <Trick>
            {trick.cards.map(cardToXml)}
          </Trick>
        })}
      </Tricks>
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
          }</rank>
    </Card>
  }

}
