import model.{Card, Hand, Player, Suit}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class PlayerSpec extends AnyWordSpec {
  "Player" should {
    "have resettable hand" in {
      val cards = List(Card(Suit.Red, 1), Card(Suit.Blue, 2), Card(Suit.Red, 3))
      val p1 = Player("p1", Hand(cards), 9)
      val p1reset = p1.resetHand

      p1.hand.count should be(3)
      p1reset.hand.count should be(0)
      p1.score should be(9)
      p1reset.score should be(9)
      p1.name should be("p1")
      p1reset.name should be("p1")
    }
  }
}
