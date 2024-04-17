import org.scalatest.wordspec.AnyWordSpec

class MainTest extends AnyWordSpec {
  "Main" should {
    "know how many players are in the game" in {
      val players = askHowManyPlayers()
      print(2)
      assert(players == 2)
    }
  }
}