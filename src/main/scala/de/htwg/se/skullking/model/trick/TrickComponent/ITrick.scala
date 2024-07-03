package de.htwg.se.skullking.model.trick.TrickComponent

import de.htwg.se.skullking.model.CardComponent.{CardDeserializer, ICard, Suit}
import de.htwg.se.skullking.model.PlayerComponent.{IPlayer, PlayerDeserializer}
import de.htwg.se.skullking.modules.{Deserializer, Serializable}
import de.htwg.se.skullking.modules.Default.given
import play.api.libs.json.{JsObject, Json}

import scala.xml.Elem

object TrickDeserializer extends Deserializer[ITrick] {
  private val TrickFactory = summon[ITrickFactory]

  override def fromXml(xml: Elem): ITrick = {
    val stack = (xml \ "Stack" \ "Element").map { element =>
      val card = CardDeserializer.fromXml((element \ "Card").head.asInstanceOf[Elem])
      val player = PlayerDeserializer.fromXml((element \ "Player").head.asInstanceOf[Elem])
      (card, player)
    }.toList
    TrickFactory(stack)
  }

  override def fromJson(json: JsObject): ITrick = {
    val stack = (json \ "stack").as[List[JsObject]].map { element =>
      val card = CardDeserializer.fromJson((element \ "card").as[JsObject])
      val player = PlayerDeserializer.fromJson((element \ "player").as[JsObject])
      (card, player)
    }
    TrickFactory(stack)
  }
}

trait ITrickFactory {
  def apply(stack: List[(ICard, IPlayer)]): ITrick
}

trait ITrick extends Serializable{
  val stack: List[(ICard, IPlayer)]

  override def toXml: Elem = {
    <Trick>
      <Stack>
        {stack.map { case (card, player) =>
          <Element>
            {card.toXml}
            {player.toXml}
          </Element>
        }}
      </Stack>
    </Trick>
  }

  override def toJson: JsObject = {
    val stackJson = stack.map { case (card, player) =>
      Json.obj(
        "card" -> card.toJson,
        "player" -> player.toJson
      )
    }
    Json.obj(
      "stack" -> stackJson
    )
  }

  def cards: List[ICard]

  def players: List[IPlayer]

  def leadSuit: Suit | Any

  def hasSkullKing: Boolean

  def hasPirate: Boolean

  def hasMermaid: Boolean

  def play(card: ICard, player: IPlayer): ITrick

  def winner: Option[IPlayer]

  def calculateBonusPoints: Int
}




