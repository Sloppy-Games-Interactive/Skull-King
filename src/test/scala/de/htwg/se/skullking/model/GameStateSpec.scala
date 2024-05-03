package de.htwg.se.skullking.model

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
  }
}
