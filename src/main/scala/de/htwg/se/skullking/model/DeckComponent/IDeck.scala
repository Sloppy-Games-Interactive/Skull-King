package de.htwg.se.skullking.model.DeckComponent

import de.htwg.se.skullking.model.CardComponent.{CardDeserializer, ICard}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.DeckFactory
import de.htwg.se.skullking.modules.Default.given
import de.htwg.se.skullking.modules.{Deserializer, Serializable}
import play.api.libs.json.{JsObject, Json}

import scala.xml.Elem

object DeckDeserializer extends Deserializer[IDeck] {

  private val DeckFactory = summon[IDeckFactory]

  override def fromXml(xml: Elem): IDeck = {
    val cards = CardDeserializer.cardListFromXml(xml)
    DeckFactory(cards)
  }

  override def fromJson(json: JsObject): IDeck = {
    val cards = (json \ "cards").as[List[JsObject]].map(CardDeserializer.fromJson)
    DeckFactory(cards)
  }
}

trait IDeck extends Serializable{
  val cards: List[ICard]
  override def toXml: Elem = {
    <Deck>
      {cards.map(_.toXml)}
    </Deck>
  }

  override def toJson: JsObject = {
    Json.obj(
      "cards" -> cards.map(_.toJson)
    )
  }

  /**
   * shuffle cards in the card list
   *
   * @return mixed card list
   */
  def shuffle(): IDeck

  def length: Int

  def getCards: List[ICard]
  /**
   *
   * @param n is the number of cards to draw
   * @return the drawn cards and the remaining deck
   */
  def draw(n: Int = 1): (List[ICard], IDeck)
}

enum DeckContent {
  case specials
  case normal
  case full
  case empty
}

trait IDeckFactory {
  def apply(kind: DeckContent = DeckContent.empty): IDeck
  def apply(cards: List[ICard]): IDeck
}
