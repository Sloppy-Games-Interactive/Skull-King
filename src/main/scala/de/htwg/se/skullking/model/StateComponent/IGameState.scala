package de.htwg.se.skullking.model.StateComponent

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.DeckComponent.{DeckContent, DeckDeserializer, IDeck}
import de.htwg.se.skullking.model.PlayerComponent.{IPlayer, PlayerDeserializer}
import de.htwg.se.skullking.model.trick.TrickComponent.{ITrick, TrickDeserializer}
import de.htwg.se.skullking.modules.{Deserializer, Serializable}
import de.htwg.se.skullking.modules.Default.given


import scala.xml.Elem
import play.api.libs.json.{JsObject, Json}

enum Phase {
  case PrepareGame
  case PrepareTricks
  case PlayTricks
  case EndGame
}

trait GameStateEvent()
case class SetPlayerLimitEvent(n: Int) extends GameStateEvent
case class AddPlayerEvent(player: IPlayer) extends GameStateEvent
case class SetPredictionEvent(player: IPlayer, prediction: Int) extends GameStateEvent
case class PlayCardEvent(player: IPlayer, card: ICard) extends GameStateEvent


object GameStateDeserializer extends Deserializer[IGameState] {
  private val GameStateFactory = summon[IGameStateFactory]

  override def fromXml(xml: Elem): IGameState = {
    val round = (xml \ "@round").text.toInt
    val phase = (xml \ "@phase").text match {
      case "PrepareGame" => Phase.PrepareGame
      case "PrepareTricks" => Phase.PrepareTricks
      case "PlayTricks" => Phase.PlayTricks
      case "EndGame" => Phase.EndGame
    }
    val playerLimit = (xml \ "@playerLimit").text.toInt
    val roundLimit = (xml \ "@roundLimit").text.toInt
    val deck = DeckDeserializer.fromXml((xml \ "Deck").head.asInstanceOf[Elem])
    val players = (xml \ "Players" \ "Player").map(node => PlayerDeserializer.fromXml(node.head.asInstanceOf[Elem])).toList
    val tricks = (xml \ "Tricks" \ "Trick").map(node => TrickDeserializer.fromXml(node.head.asInstanceOf[Elem])).toList

    GameStateFactory(
      round = round,
      phase = phase,
      playerLimit = playerLimit,
      roundLimit = roundLimit,
      deck = deck,
      players = players,
      tricks = tricks
    )
  }

  override def fromJson(json: JsObject): IGameState = {
    val round = (json \ "round").as[Int]
    val phase = (json \ "phase").as[String] match {
      case "PrepareGame" => Phase.PrepareGame
      case "PrepareTricks" => Phase.PrepareTricks
      case "PlayTricks" => Phase.PlayTricks
      case "EndGame" => Phase.EndGame
    }
    val playerLimit = (json \ "playerLimit").as[Int]
    val roundLimit = (json \ "roundLimit").as[Int]
    val deck = DeckDeserializer.fromJson((json \ "deck").as[JsObject])
    val players = (json \ "players").as[List[JsObject]].map(PlayerDeserializer.fromJson)
    val tricks = (json \ "tricks").as[List[JsObject]].map(TrickDeserializer.fromJson)

    GameStateFactory(
      round = round,
      phase = phase,
      playerLimit = playerLimit,
      roundLimit = roundLimit,
      deck = deck,
      players = players,
      tricks = tricks
    )
  }
}

trait IGameState extends Serializable{
  val phase: Phase
  val playerLimit: Int
  val players: List[IPlayer]
  val round: Int
  val tricks: List[ITrick]
  val deck: IDeck
  val roundLimit: Int
  val lastTrickWinner: Option[IPlayer]

  override def toXml: Elem = {
    <GameState round={round.toString} phase={phase.toString} playerLimit={playerLimit.toString} roundLimit={roundLimit.toString}>
        {deck.toXml}
      <Players>
        {players.map(_.toXml)}
      </Players>
      <Tricks>
        {tricks.map(_.toXml)}
      </Tricks>
    </GameState>
  }

  override def toJson: JsObject = {
    Json.obj(
      "round" -> round,
      "phase" -> phase.toString,
      "playerLimit" -> playerLimit,
      "roundLimit" -> roundLimit,
      "players" -> players.map(_.toJson),
      "deck" -> deck.toJson,
      "tricks" -> tricks.map(_.toJson)
    )
  }

  def handleEvent(event: GameStateEvent): IGameState

  def activePlayer: Option[IPlayer]
  
  def activeTrick: Option[ITrick]
}

trait IGameStateFactory {
  def apply(round: Int, phase: Phase, playerLimit: Int, roundLimit: Int, deck: IDeck, players: List[IPlayer], tricks: List[ITrick]): IGameState
}