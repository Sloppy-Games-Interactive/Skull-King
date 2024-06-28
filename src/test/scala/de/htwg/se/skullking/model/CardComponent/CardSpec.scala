package de.htwg.se.skullking.model.CardComponent

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.{CardFactory, JokerCard, StandardCard}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec {
  "Card" should {
    "have a readable" in {
      val card = CardFactory(Suit.Red, 1)
      card.suit.readable should be("\uD83D\uDFE5")
    }

    "correctly display its suit and value in its toString method" in {
      val card = CardFactory(Suit.Red, 1)
      card.toString should be("🟥 1")
    }

    "correctly display its suit when it is a special card" in {
      val pirate = CardFactory(Suit.Pirate)
      val card = CardFactory(Suit.Joker).asInstanceOf[JokerCard].playAs(JokerBehaviour.Pirate)
      card.toString should be("🃏 as 🏴‍☠️")
      pirate.toString should be("🏴‍☠️")
    }

    "joker should be playable as escape and pirate" in {
      val card = JokerCard()
      card.playAs(JokerBehaviour.Escape).toString should be("🃏 as 🏝️")
      card.playAs(JokerBehaviour.Pirate).toString should be("🃏 as 🏴‍☠️")
    }

    "be identifiable as Trump" in {
      val card = CardFactory(Suit.Black, 1)
      val r1 = CardFactory(Suit.Red, 1)
      val p = CardFactory(Suit.Pirate)
      
      card.isTrump should be(true)
      r1.isTrump should be(false)
      p.isTrump should be(false)
    }

    "be identifiable as Special" in {
      val p = CardFactory(Suit.Pirate)
      val r1 = CardFactory(Suit.Red, 1)
      val b1 = CardFactory(Suit.Black, 1)
      
      p.isSpecial should be(true)
      r1.isSpecial should be(false)
      b1.isSpecial should be(false)
    }

    "be serializable as json" in {
      val r1: StandardCard = CardFactory(Suit.Red, 1)
      val pirate = CardFactory(Suit.Pirate)
      val joker = JokerCard()
      val jPirate = joker.playAs(JokerBehaviour.Pirate)
      val jEscape = joker.playAs(JokerBehaviour.Escape)

      val jsonR1 = r1.toJson
      val jsonPirate = pirate.toJson
      val jsonJoker = joker.toJson
      val jsonJPirate = jPirate.toJson
      val jsonJEscape = jEscape.toJson

      (jsonR1 \ "suit").as[String] should be("Red")
      (jsonR1 \ "value").asOpt[Int] should be(Some(1))

      (jsonPirate \ "suit").as[String] should be("Pirate")

      (jsonJoker \ "suit").as[String] should be("Joker")
      (jsonJoker \ "as").as[String] should be("None")
      (jsonJPirate \ "as").as[String] should be("Pirate")
      (jsonJEscape \ "as").as[String] should be("Escape")

      val r1Parsed: StandardCard = CardDeserializer.fromJson(jsonR1).asInstanceOf[StandardCard]
      val pirateParsed = CardDeserializer.fromJson(jsonPirate)
      val jokerParsed = CardDeserializer.fromJson(jsonJoker)
      val jPirateParsed = CardDeserializer.fromJson(jsonJPirate)
      val jEscapeParsed = CardDeserializer.fromJson(jsonJEscape)

      r1Parsed.suit should be(Suit.Red)
      r1Parsed.value should be(1)

      pirateParsed.suit should be(Suit.Pirate)

      jokerParsed.suit should be(Suit.Joker)
      jokerParsed.asInstanceOf[JokerCard].as should be(JokerBehaviour.None)
      jPirateParsed.asInstanceOf[JokerCard].as should be(JokerBehaviour.Pirate)
      jEscapeParsed.asInstanceOf[JokerCard].as should be(JokerBehaviour.Escape)
    }


    "be serializable as xml" in {
      val r1: StandardCard = CardFactory(Suit.Red, 1)
      val pirate = CardFactory(Suit.Pirate)
      val joker = JokerCard()
      val jPirate = joker.playAs(JokerBehaviour.Pirate)
      val jEscape = joker.playAs(JokerBehaviour.Escape)

      val xmlR1 = r1.toXml
      val xmlPirate = pirate.toXml
      val xmlJoker = joker.toXml
      val xmlJPirate = jPirate.toXml
      val xmlJEscape = jEscape.toXml

      (xmlR1 \ "suit").text should be("Red")
      (xmlR1 \ "value").text should be("1")
      (xmlPirate \ "suit").text should be("Pirate")
      (xmlJoker \ "suit").text should be("Joker")
      (xmlJoker \ "as").text should be("None")
      (xmlJPirate \ "as").text should be("Pirate")
      (xmlJEscape \ "as").text should be("Escape")

      val r1Parsed: StandardCard = CardDeserializer.fromXml(xmlR1).asInstanceOf[StandardCard]
      val pirateParsed = CardDeserializer.fromXml(xmlPirate)
      val jokerParsed = CardDeserializer.fromXml(xmlJoker)
      val jPirateParsed = CardDeserializer.fromXml(xmlJPirate)
      val jEscapeParsed = CardDeserializer.fromXml(xmlJEscape)

      r1Parsed.suit should be(Suit.Red)
      r1Parsed.value should be(1)
      pirateParsed.suit should be(Suit.Pirate)
      jokerParsed.suit should be(Suit.Joker)
      jokerParsed.asInstanceOf[JokerCard].as should be(JokerBehaviour.None)
      jPirateParsed.asInstanceOf[JokerCard].as should be(JokerBehaviour.Pirate)
      jEscapeParsed.asInstanceOf[JokerCard].as should be(JokerBehaviour.Escape)

    }

  }
}