package controller

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import util.Observer

class ControllerSpec extends AnyWordSpec {
  "Controller" when {
    "observed by an Observer" should {
      class TestObserver extends Observer {
        var updated: Int = 0
        override def update: Unit = updated += 1
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
    }
  }
}