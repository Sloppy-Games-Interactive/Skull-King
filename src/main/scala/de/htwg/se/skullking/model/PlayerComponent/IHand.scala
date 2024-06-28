package de.htwg.se.skullking.model.PlayerComponent

import de.htwg.se.skullking.model.CardComponent.{CardDeserializer, ICard}
import de.htwg.se.skullking.model.DeckComponent.IDeck
import de.htwg.se.skullking.modules.{Deserializer, Serializable}
import de.htwg.se.skullking.modules.Default.given
import play.api.libs.json.{JsObject, Json}

import scala.xml.Elem

object HandDeserializer extends Deserializer[IHand] {
  private val HandFactory = summon[IHandFactory]

  override def fromXml(xml: Elem): IHand = {
    val cards = CardDeserializer.cardListFromXml(xml)
    HandFactory(cards)
  }

  override def fromJson(json: JsObject): IHand = {
    val cards = (json \ "cards").as[List[JsObject]].map(CardDeserializer.fromJson)
    HandFactory(cards)
  }
}
/**
 * Hand class for
 *
 * @param cards take a list of playable cards, default empty list of card
 */
trait IHand extends Serializable {
  val cards: List[ICard]

  override def toXml: Elem = {
    <Hand>
      {cards.map(_.toXml)}
    </Hand>
  }

  override def toJson: JsObject = {
    Json.obj(
      "cards" -> cards.map(_.toJson)
    )
  }
  
  def count: Int
  
  def indexOf(card: ICard): Option[Int]

  def play(idx: Int): (ICard, IHand)

  def drawFromDeck(deck: IDeck, n: Int): (IDeck, IHand)
}

trait IHandFactory {
  def apply(cards: List[ICard]): IHand
}
