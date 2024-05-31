package de.htwg.se.skullking.controller

import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec {

  "Controller" when {
    "doing undo/redo" should {
      "handle undo/redo player" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.addPlayer("foo")
        ctrl.addPlayer("bar")

        ctrl.state.players should have length 2
        ctrl.state.players.map(_.name) should contain allOf("foo", "bar")

        ctrl.undo
        ctrl.state.players should have length 1
        ctrl.state.players.map(_.name) should contain only "foo"

        ctrl.redo
        ctrl.state.players should have length 2
        ctrl.state.players.map(_.name) should contain allOf("foo", "bar")
      }

      "handle undo/redo round" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.prepareRound

        ctrl.state.round should be(1)

        ctrl.undo
        ctrl.state.round should be(0)

        ctrl.redo
        ctrl.state.round should be(1)
      }

      "handle undo/redo prediction" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.addPlayer("foo")
        ctrl.prepareRound
        ctrl.setPrediction(ctrl.state.players.head, 3)

        ctrl.state.players.head.prediction should be(3)

        ctrl.undo
        ctrl.state.players.head.prediction should be(0)

        ctrl.redo
        ctrl.state.players.head.prediction should be(3)
      }

      "handle undo/redo cards" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.addPlayer("foo")
        ctrl.prepareRound
        ctrl.dealCards

        ctrl.state.players.head.hand.count should be(1)

        ctrl.undo
        ctrl.state.players.head.hand.count should be(0)

        ctrl.redo
        ctrl.state.players.head.hand.count should be(1)
      }

      "handle undo/redo new game" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.addPlayer("foo")

        ctrl.state.players should have length 1
        ctrl.state.players.map(_.name) should contain only "foo"

        ctrl.newGame
        ctrl.state.players should have length 0

        ctrl.undo
        ctrl.state.players should have length 1
        ctrl.state.players.map(_.name) should contain only "foo"

        ctrl.redo
        ctrl.state.players should have length 0
      }

      "handle empty undo/redo" in {
        val ctrl = Controller()

        ctrl.undo
        ctrl.state.players should have length 0

        ctrl.redo
        ctrl.state.players should have length 0
      }

      "handle undo/redo play card" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.addPlayer("foo")
        ctrl.prepareRound
        ctrl.dealCards
        ctrl.playCard(ctrl.state.players.head, 0)

        ctrl.state.players.head.hand.count should be(0)

        ctrl.undo
        ctrl.state.players.head.hand.count should be(1)

        ctrl.redo
        ctrl.state.players.head.hand.count should be(0)
      }
    }

    "observed by an Observer" should {
      class TestObserver extends Observer {
        var updated: Int = 0
        override def update(e: ObservableEvent): Unit = updated += 1
      }

      val controller = Controller()
      "start new game" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame

        observer.updated should be(1)
        controller.state.round should be(0)
      }

      "add player" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.addPlayer("foo")

        observer.updated should be(2)
        controller.state.players should have length 1
        controller.state.players.head.name should be("foo")
      }

      "prepare round" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.prepareRound

        observer.updated should be(2)
        controller.state.round should be(1)
      }

      "deal cards" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.addPlayer("foo")
        controller.prepareRound
        controller.dealCards

        observer.updated should be(4)
        controller.state.players.head.hand.count should be(1)
      }

      "start a new trick" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.startTrick
        observer.updated should be(2)
        assert(true) // TODO implement actual test - assert(true) == BÖÖÖÖÖÖÖSE
      }

      "set prediction" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.addPlayer("foo")
        controller.prepareRound
        controller.setPrediction(controller.state.players.head, 3)

        observer.updated should be(4)
        controller.state.players.head.prediction should be(3)
      }

      "quit" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.quit

        observer.updated should be(2)
        assert(true) // TODO implement actual test - assert(true) == BÖÖÖÖÖÖÖSE
      }

      "play card" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.addPlayer("foo")
        controller.prepareRound
        controller.dealCards
        controller.playCard(controller.state.players.head, 0)

        observer.updated should be(5)
        controller.state.players.head.hand.count should be(0)
      }
    }
  }
}