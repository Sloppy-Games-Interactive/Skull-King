package de.htwg.se.skullking.model.PlayerComponent

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.Suit
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.JsObject

import scala.language.postfixOps
import scala.xml.Elem

class PlayerSpec extends AnyWordSpec {
  "Player" should {
    "have defaults" in {
      val p1 = Player("")
      p1.name should be("")
      p1.hand.count should be(0)
      p1.score should be(0)
      p1.prediction should be(None)
    }

    "have a name" in {
      val p1 = Player("p1", Hand(List()), 9, Some(0))
      p1.name should be("p1")
    }

    "have resettable hand" in {
      val cards = List(CardFactory(Suit.Red, 1), CardFactory(Suit.Blue, 2), CardFactory(Suit.Red, 3))
      val p1 = Player("p1", Hand(cards), 9)
      val p1reset = p1.resetHand

      p1.hand.count should be(3)
      p1reset.hand.count should be(0)
      p1.score should be(9)
      p1reset.score should be(9)
      p1.name should be("p1")
      p1reset.name should be("p1")
    }

    "have a score" in {
      val p1 = Player("p1", Hand(List()), 9)
      p1.score should be(9)
    }

    "have a resettable prediction" in {
      val p1 = Player("p1", Hand(List()), 9, Some(0))
      val p1reset = p1.resetPrediction

      p1.prediction should be(Some(0))
      p1reset.prediction should be(None)
    }

    "have a toString" in {
      val hand = Hand(List(CardFactory(Suit.Red, 1), CardFactory(Suit.Blue, 2), CardFactory(Suit.Red, 3)))
      val p1 = Player("p1", hand, 9, Some(0))
      p1.toString should be(s"p1: 9, ${hand.toString}, prediction: 0")

      val p2 = Player("p2", hand, 9)
      p2.toString should be(s"p2: 9, ${hand.toString}, prediction: -")
    }

    "be serializable as json" in {
      val hand = Hand(List(CardFactory(Suit.Red, 1), CardFactory(Suit.Blue, 2), CardFactory(Suit.Red, 3)))
      val p1 = Player("p1", hand, 9, Some(0), true)
      val json = p1.toJson

      (json \ "name").as[String] should be("p1")
      HandDeserializer.fromJson((json \ "hand").as[JsObject]).cards should contain theSameElementsAs hand.cards
      (json \ "score").as[Int] should be(9)
      (json \ "prediction").asOpt[Int] should be(Some(0))
      (json \ "active").as[Boolean] should be(true)

      val newPlayer = PlayerDeserializer.fromJson(json)

      p1.name should be(newPlayer.name)
      p1.hand.cards should contain theSameElementsAs newPlayer.hand.cards
      p1.score should be(newPlayer.score)
      p1.prediction should be(newPlayer.prediction)
      p1.active should be(newPlayer.active)
    }

    "be serializable as xml" in {
      val hand = Hand(List(CardFactory(Suit.Red, 1), CardFactory(Suit.Blue, 2), CardFactory(Suit.Red, 3)))
      val p1 = Player("p1", hand, 9, Some(0), true)
      val xml = p1.toXml

      (xml \ "name").text should be("p1")
      HandDeserializer.fromXml((xml \ "Hand").head.asInstanceOf[Elem]).cards should contain theSameElementsAs hand.cards
      (xml \ "score").text.toInt should be(9)
      
      (xml \ "active").text.toBoolean should be(true)

      val newPlayer = PlayerDeserializer.fromXml(xml)

      p1.name should be(newPlayer.name)
      p1.hand.cards should contain theSameElementsAs newPlayer.hand.cards
      p1.score should be(newPlayer.score)
      p1.prediction should be(newPlayer.prediction)
      p1.active should be(newPlayer.active)
    }
  }
}
