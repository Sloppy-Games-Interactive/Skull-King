package de.htwg.se.skullking.model

import de.htwg.se.skullking.model.card.Suit.Blue
import de.htwg.se.skullking.model.card.{Card, Suit}
import de.htwg.se.skullking.model.player.{Hand, Player}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GameStateSpec extends AnyWordSpec {

  val gameState: GameState = GameState()

  "GameState" should {

    "add a player" in {
      val p1 = Player("foo")
      val p2 = Player("bar")

      val s1 = gameState.addPlayer(p1)
      s1.players.head should be(p1)
      s1.players should have length 1

      val s2 = s1.addPlayer(p2)
      s2.players should have length 2
    }
    "deal cards" in {
      val p1 = Player("foo")
      val p2 = Player("bar")

      val s1 = GameState(List(), 4).addPlayer(p1).addPlayer(p2).startNewRound

      val s2 = s1.dealCards

      s2.players.head.hand.count shouldBe 5
    }

    "playCard" should {
      "play a valid card" in {
        val p1 = Player("foo", Hand(List(Card(Blue, 1), Card(Blue, 2), Card(Blue, 3), Card(Blue, 4), Card(Blue, 5))), 0)
        val p2 = Player("bar", Hand(List(Card(Blue, 6), Card(Blue, 7), Card(Blue, 8), Card(Blue, 9), Card(Blue, 10))), 0)

        val s1 = GameState(List(p1, p2), 4)

        val s2 = s1.playCard(p1, 0)

        s2.players.head.hand.count shouldBe 4
      }

      "play not an invalid card" in {
        val p1 = Player("foo", Hand(List(Card(Blue, 1), Card(Blue, 2), Card(Blue, 3), Card(Blue, 4), Card(Blue, 5))), 0)
        val p2 = Player("bar", Hand(List(Card(Blue, 6), Card(Blue, 7), Card(Blue, 8), Card(Blue, 9), Card(Blue, 10))), 0)

        val s1 = GameState(List(p1, p2), 4)

        val s2 = s1.playCard(p1, 6)

        s2.players.head.hand.count shouldBe 5
      }

      "play not a card from an empty hand" in {
        val p1 = Player("foo", Hand(List()), 0)
        val p2 = Player("bar", Hand(List(Card(Blue, 6), Card(Blue, 7), Card(Blue, 8), Card(Blue, 9), Card(Blue, 10))), 0)

        val s1 = GameState(List(p1, p2), 4)

        val s2 = s1.playCard(p1, 0)

        s2.players.head.hand.count shouldBe 0
      }

      "play not a card from an empty hand with an invalid index" in {
        val p1 = Player("foo", Hand(List()), 0)
        val p2 = Player("bar", Hand(List(Card(Blue, 6), Card(Blue, 7), Card(Blue, 8), Card(Blue, 9), Card(Blue, 10))), 0)

        val s1 = GameState(List(p1, p2), 4)

        val s2 = s1.playCard(p1, 5)

        s2.players.head.hand.count shouldBe 0
      }

    }

    "start new round" in {
      val p1 = Player("foo")
      val p2 = Player("bar")

      val s1 = GameState(List(), 1).addPlayer(p1).addPlayer(p2)
      val s2 = s1.startNewRound

      s1.round should be(1)
      s2.round should be(2)
    }

    "get status as table" in {
      val p1 = Player("foo")
      val p2 = Player("bar")

      val s1 = GameState(List(), 0).addPlayer(p1).addPlayer(p2)
      val s2 = s1.startNewRound

      val s1Table = s1.getStatusAsTable
      val s2Table = s2.getStatusAsTable

      s1Table should include("Round: 0")
      s1Table should include("Players:")
      s1Table should include("foo")
      s1Table should include("bar")
      
      s2Table should include("Round: 1")
      s2Table should include("Players:")
      s2Table should include("foo")
      s2Table should include("bar")
    }

    "set prediction" in {
      val p1 = Player("foo")
      val p2 = Player("bar")

      val s1 = GameState(List(), 4).addPlayer(p1).addPlayer(p2)
      val s2 = s1.startNewRound

      val s3 = s2.setPrediction(p2, 3)
      s3.players.head.prediction should be(3)
    }

  }
}
