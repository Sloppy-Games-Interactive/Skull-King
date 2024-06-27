package de.htwg.se.skullking.model.StateComponent

import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.CardFactory
import de.htwg.se.skullking.model.CardComponent.Suit
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, Player}
import de.htwg.se.skullking.model.PlayerComponent.PlayerDeserializer
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.trick.TrickComponent.TrickBaseImpl.Trick
import de.htwg.se.skullking.model.trick.TrickComponent.TrickDeserializer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.JsObject

import scala.xml.Elem

class GameStateSpec extends AnyWordSpec {
  "A GameState" should {
    "handle SetPlayerLimitEvent correctly" in {
      val gameState = GameState()
      val updatedGameState = gameState.handleEvent(SetPlayerLimitEvent(4))
      updatedGameState.playerLimit should be(4)
    }

    "handle AddPlayerEvent correctly" in {
      val gameState = GameState(playerLimit = 2)
      val updatedGameState = gameState.handleEvent(AddPlayerEvent(Player("Alice")))
      updatedGameState.players.map(_.name) should contain("Alice")
    }

    "handle SetPredictionEvent correctly" in {
      val player = Player("Alice")
      val gameState = GameState(phase = Phase.PrepareTricks, players = List(player))
      val updatedGameState = gameState.handleEvent(SetPredictionEvent(player, 3))
      updatedGameState.players.head.prediction should be(Some(3))
    }

    "handle PlayCardEvent correctly" in {
      val player = Player("Alice", hand = Hand(List(CardFactory(Suit.Red, 1))))
      val player2 = Player("Bob", hand = Hand(List(CardFactory(Suit.Red, 2))))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 1,
        playerLimit = 2
      )
      val updatedGameState = gameState.handleEvent(PlayCardEvent(player, player.hand.cards.head))
      updatedGameState.tricks.head.cards.head.suit should be(Suit.Red)
    }

    "ignore events that are not applicable in the current phase" in {
      val gameState = GameState(phase = Phase.EndGame)
      val updatedGameState = gameState.handleEvent(AddPlayerEvent(Player("Alice")))
      updatedGameState should be(gameState)
    }

    "end the round correctly when all tricks are played" in {
      val r1 = CardFactory(Suit.Red, 1)
      val r2 = CardFactory(Suit.Red, 2)
      val player = Player("Alice", hand = Hand(List(r1)))
      val player2 = Player("Bob", hand = Hand(List(r2)))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 1,
      )
      gameState.round should be(1)
      gameState.phase should be(Phase.PlayTricks)
      gameState.tricks.length should be(1)

      gameState.players.head.hand.count should be(1)
      val updatedGameState1 = gameState.handleEvent(PlayCardEvent(player, r1))
      updatedGameState1.players.head.hand.count should be(0)
      updatedGameState1.tricks.head.cards.length should be(1)
      updatedGameState1.phase should be(Phase.PlayTricks)

      val updatedGameState2 = updatedGameState1.handleEvent(PlayCardEvent(player2, r2))
      updatedGameState2.activeTrick should be(None)
      updatedGameState2.phase should be(Phase.PrepareTricks)
    }

    "play n tricks for round n" in {
      val r1 = CardFactory(Suit.Red, 1)
      val r2 = CardFactory(Suit.Red, 2)
      val player = Player("Alice", hand = Hand(List(r1)), prediction = Some(1))
      val player2 = Player("Bob", hand = Hand(List(r2)), prediction = Some(1))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 2,
        roundLimit = 2
      )
      gameState.round should be(2)
      gameState.phase should be(Phase.PlayTricks)
      gameState.tricks.length should be(1)

      gameState.players.head.hand.count should be(1)
      val updatedGameState1 = gameState.handleEvent(PlayCardEvent(player, r1)).handleEvent(PlayCardEvent(player2, r2))
      updatedGameState1.tricks.length should be(2)
    }

    "calculate correct scores when predicting 0" in {
      val player = Player("Alice", hand = Hand(List(CardFactory(Suit.Red, 1))), prediction = Some(0))
      val player2 = Player("Bob", hand = Hand(List(CardFactory(Suit.Red, 2))), prediction = Some(0))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 1,
        roundLimit = 1
      )
      gameState.round should be(1)
      gameState.phase should be(Phase.PlayTricks)
      gameState.tricks.length should be(1)

      gameState.players.head.hand.count should be(1)
      val updatedGameState1 = gameState.handleEvent(PlayCardEvent(player, player.hand.cards.head))
      updatedGameState1.players.head.hand.count should be(0)
      updatedGameState1.tricks.head.cards.length should be(1)
      updatedGameState1.phase should be(Phase.PlayTricks)

      val updatedGameState2 = updatedGameState1.handleEvent(PlayCardEvent(player2, player2.hand.cards.head))
      updatedGameState2.activeTrick should be(None)
      updatedGameState2.phase should be(Phase.EndGame)
      updatedGameState2.players.head.score should be(10)
      updatedGameState2.players(1).score should be(-10)
    }

    "calculate correct scores when predicting 1" in {
      val player = Player("Alice", hand = Hand(List(CardFactory(Suit.Red, 1))), prediction = Some(1))
      val player2 = Player("Bob", hand = Hand(List(CardFactory(Suit.Red, 2))), prediction = Some(1))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 1,
        roundLimit = 1
      )
      gameState.round should be(1)
      gameState.phase should be(Phase.PlayTricks)
      gameState.tricks.length should be(1)

      gameState.players.head.hand.count should be(1)
      val updatedGameState1 = gameState.handleEvent(PlayCardEvent(player, player.hand.cards.head))
      updatedGameState1.players.head.hand.count should be(0)
      updatedGameState1.tricks.head.cards.length should be(1)
      updatedGameState1.phase should be(Phase.PlayTricks)

      val updatedGameState2 = updatedGameState1.handleEvent(PlayCardEvent(player2, player2.hand.cards.head))
      updatedGameState2.activeTrick should be(None)
      updatedGameState2.phase should be(Phase.EndGame)
      updatedGameState2.players.head.score should be(-10)
      updatedGameState2.players(1).score should be(20)
    }

    "end the game correctly when all rounds are played" in {
      val player = Player("Alice", hand = Hand(List(CardFactory(Suit.Red, 1))))
      val player2 = Player("Bob", hand = Hand(List(CardFactory(Suit.Red, 2))))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 1,
        roundLimit = 1
      )
      gameState.round should be(1)
      gameState.phase should be(Phase.PlayTricks)
      gameState.tricks.length should be(1)

      gameState.players.head.hand.count should be(1)
      val updatedGameState1 = gameState.handleEvent(PlayCardEvent(player, player.hand.cards.head))
      updatedGameState1.players.head.hand.count should be(0)
      updatedGameState1.tricks.head.cards.length should be(1)
      updatedGameState1.phase should be(Phase.PlayTricks)

      val updatedGameState2 = updatedGameState1.handleEvent(PlayCardEvent(player2, player2.hand.cards.head))
      updatedGameState2.activeTrick should be(None)
      updatedGameState2.phase should be(Phase.EndGame)
    }

    "be serializable as json" in {
      val player = Player("Alice", hand = Hand(List(CardFactory(Suit.Red, 1))))
      val player2 = Player("Bob", hand = Hand(List(CardFactory(Suit.Red, 2))))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 1,
        roundLimit = 1
      )
      val json = gameState.toJson

      (json \ "phase").as[String] should be("PlayTricks")
      (json \ "players").as[List[JsObject]].length should be(2)
      (json \ "tricks").as[List[JsObject]].length should be(1)
      (json \ "round").as[Int] should be(1)
      (json \ "roundLimit").as[Int] should be(1)
      val jsonPlayers = (json \ "players").as[List[JsObject]].map { player =>
        PlayerDeserializer.fromJson(player)
      }
      jsonPlayers.head.name should be("Alice")
      jsonPlayers(1).name should be("Bob")

      val newGameState = GameStateDeserializer.fromJson(json)

      newGameState.phase should be(Phase.PlayTricks)
      newGameState.players.head.name should be("Alice")
      newGameState.players(1).name should be("Bob")
      newGameState.tricks.length should be(1)
      newGameState.round should be(1)
      newGameState.roundLimit should be(1)
    }

    "be xml serializable" in {
      val player = Player("Alice", hand = Hand(List(CardFactory(Suit.Red, 1))))
      val player2 = Player("Bob", hand = Hand(List(CardFactory(Suit.Red, 2))))
      val gameState = GameState(
        phase = Phase.PlayTricks,
        players = List(player, player2),
        tricks = List(Trick()),
        round = 1,
        roundLimit = 1
      )
      val xml = gameState.toXml

      val xmlPlayers = (xml \ "Players" \ "Player").map(node => PlayerDeserializer.fromXml(node.head.asInstanceOf[Elem])).toList
      val tricks = (xml \ "Tricks" \ "Trick").map(node => TrickDeserializer.fromXml(node.head.asInstanceOf[Elem])).toList

      (xml \ "@phase").text should be("PlayTricks")
      xmlPlayers.length should be(2)
      tricks.length should be(1)
      (xml \ "@round").text should be("1")
      (xml \ "@roundLimit").text should be("1")

      xmlPlayers.head.name should be("Alice")
      xmlPlayers(1).name should be("Bob")

      val newGameState = GameStateDeserializer.fromXml(xml)

      newGameState.phase should be(Phase.PlayTricks)
      newGameState.players.head.name should be("Alice")
      newGameState.players(1).name should be("Bob")
      newGameState.tricks.length should be(1)
      newGameState.round should be(1)
      newGameState.roundLimit should be(1)
    }

  }
}
