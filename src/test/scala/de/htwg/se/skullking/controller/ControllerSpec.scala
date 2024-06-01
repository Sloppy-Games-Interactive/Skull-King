package de.htwg.se.skullking.controller

import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec {

  "Controller" when {
    "doing undo/redo" should {
      "handle undo/redo player limit" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.setPlayerLimit(2)

        ctrl.state.playerLimit should be(2)

        ctrl.undo
        ctrl.state.playerLimit should be(0)

        ctrl.redo
        ctrl.state.playerLimit should be(2)
      }

      "handle undo/redo player" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.setPlayerLimit(2)
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
        ctrl.setPlayerLimit(2)
        ctrl.addPlayer("foo")
        ctrl.addPlayer("bar")

        ctrl.state.round should be(1)

        ctrl.undo
        ctrl.state.round should be(0)

        ctrl.redo
        ctrl.state.round should be(1)
      }

      "handle undo/redo prediction" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.setPlayerLimit(2)
        ctrl.addPlayer("foo")
        ctrl.addPlayer("bar")
        ctrl.setPrediction(ctrl.state.players.head, 1)

        ctrl.state.players.head.prediction should be(Some(1))

        ctrl.undo
        ctrl.state.players.head.prediction should be(None)

        ctrl.redo
        ctrl.state.players.head.prediction should be(Some(1))
      }

      "handle undo/redo new game" in {
        val ctrl = Controller()

        ctrl.newGame
        ctrl.setPlayerLimit(2)
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
        ctrl.setPlayerLimit(2)
        ctrl.addPlayer("foo")
        ctrl.addPlayer("bar")
        ctrl.setPrediction(ctrl.state.activePlayer.get, 1)
        ctrl.setPrediction(ctrl.state.activePlayer.get, 1)

        ctrl.state.players.head.hand.count should be(1)

        ctrl.playCard(ctrl.state.activePlayer.get, 0)

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

        observer.updated should be(2)
        controller.state.round should be(0)
      }

      "add player" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.setPlayerLimit(2)
        controller.addPlayer("foo")
        controller.addPlayer("bar")

        observer.updated should be(8)
        controller.state.players should have length 2
        controller.state.players.head.name should be("foo")
      }

      "set prediction" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.setPlayerLimit(2)
        controller.addPlayer("foo")
        controller.addPlayer("bar")
        controller.setPrediction(controller.state.players.head, 3)

        observer.updated should be(10)
        controller.state.players.head.prediction should be(Some(3))
      }

      "quit" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.quit

        observer.updated should be(3)
        assert(true) // TODO implement actual test - assert(true) == BÖÖÖÖÖÖÖSE
      }

      "play card" in {
        val observer = TestObserver()
        controller.add(observer)

        controller.newGame
        controller.setPlayerLimit(2)
        controller.addPlayer("foo")
        controller.addPlayer("bar")
        controller.setPrediction(controller.state.activePlayer.get, 1)
        controller.setPrediction(controller.state.activePlayer.get, 1)
        controller.playCard(controller.state.activePlayer.get, 0)

        observer.updated should be(14)
        controller.state.players.head.hand.count should be(0)
      }
    }
  }
}