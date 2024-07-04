package de.htwg.se.skullking.model.CardComponent

import de.htwg.se.skullking.modules.Default.given
import de.htwg.se.skullking.modules.{Deserializer, Serializable}
import play.api.libs.json.{JsObject, Json}

import scala.xml.Elem


object CardDeserializer extends Deserializer[ICard] {

  private val CardFactory = summon[ICardFactory]

  def cardListFromXml(xml: Elem): List[ICard] = {
    (xml \ "Card").flatMap(node => node.headOption.collect {
      case e: Elem => fromXml(e)
    }).toList
  }

  override def fromXml(xml: Elem): ICard = {
    val suit = (xml \ "suit").text
    val rank = (xml \ "value").text
    val as = (xml \ "as").text match {
      case "Pirate" => JokerBehaviour.Pirate
      case "Escape" => JokerBehaviour.Escape
      case _ => JokerBehaviour.None
    }

    suit match {
      case "Joker" => CardFactory(as)
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

  override def fromJson(json: JsObject): ICard = {
    val suit = (json \ "suit").as[String]
    val value = (json \ "value").asOpt[Int]
    val as = (json \ "as").asOpt[String] match {
      case Some("Pirate") => JokerBehaviour.Pirate
      case Some("Escape") => JokerBehaviour.Escape
      case _ => JokerBehaviour.None
    }


    suit match {
      case "Joker" => CardFactory(as)
      case "Pirate" => CardFactory(Suit.Pirate)
      case "Mermaid" => CardFactory(Suit.Mermaid)
      case "SkullKing" => CardFactory(Suit.SkullKing)
      case "Escape" => CardFactory(Suit.Escape)
      case "Blue" => CardFactory(Suit.Blue, value.get)
      case "Red" => CardFactory(Suit.Red, value.get)
      case "Yellow" => CardFactory(Suit.Yellow, value.get)
      case "Black" => CardFactory(Suit.Black, value.get)
    }
  }

}

trait ICard extends Serializable{
  val suit: Suit

  override def toXml: scala.xml.Elem = {
    <Card>
      <suit>{suit}</suit>
      {this match{
        case standardCard: IStandardCard => {
          <value>{standardCard.value}</value>
        }
        case jokerCard: IJokerCard => {
          <as>{jokerCard.as}</as>
        }
        case _ =>
      }
        }
    </Card>
  }

  override def toJson: JsObject = {
    Json.obj(
      "suit" -> suit.toString,
      this match {
        case standardCard: IStandardCard => "value" -> standardCard.value
        case jokerCard: IJokerCard => "as" -> jokerCard.as.toString
        case _ => "value" -> 0
      }
    )
  }

  def isSpecial: Boolean

  def isTrump: Boolean
}

trait IStandardCard extends ICard {
  val value: Int
}
trait ISpecialCard extends ICard

enum JokerBehaviour {
  case Pirate
  case Escape
  case None

  def readable: String = this match {
    case Pirate => Suit.Pirate.readable
    case Escape => Suit.Escape.readable
    case None => Suit.Joker.readable
  }
}

trait IJokerCard extends ISpecialCard {
  val as: JokerBehaviour
  def playAs(behaviour: JokerBehaviour): IJokerCard
}

trait ICardFactory {
  def apply(suit: Suit, value: Int): ICard
  def apply(suit: Suit): ICard
  def apply(as: JokerBehaviour): IJokerCard
}
