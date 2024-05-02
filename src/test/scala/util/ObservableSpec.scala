package util

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ObservableSpec extends AnyWordSpec {
  "Observable" should {
    class TestObserver extends Observer {
      var updated: Int = 0
      override def update: Unit = updated += 1
    }

    val observable = Observable()
    val observer = TestObserver()
    "add observer" in {
      observable.add(observer)
      observable.subscribers should have length 1
    }

    "notify observer" in {
      observable.notifyObservers()
      observer.updated should be(1)
    }

    "remove observer" in {
      observable.remove(observer)
      observable.subscribers should have length 0
    }
  }
}