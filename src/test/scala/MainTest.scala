import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

class MainTest extends AnyWordSpec {
  "askHowManyPlayers" should {
    "return the correct number of players" in {
      val input = new ByteArrayInputStream("3\n".getBytes(StandardCharsets.UTF_8))
      val output = new ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(new PrintStream(output)) {
          assert(askHowManyPlayers() == 3)
        }
      }
      assert(output.toString(StandardCharsets.UTF_8.name()).trim == "How many players?")
    }

    "throw an exception for invalid input" in {
      val input = new ByteArrayInputStream("invalid\n".getBytes(StandardCharsets.UTF_8))
      Console.withIn(input) {
        assertThrows[NumberFormatException] {
          askHowManyPlayers()
        }
      }
    }

    "ask again if the number of players is too small" in {
      val input = new ByteArrayInputStream("1\n3\n".getBytes(StandardCharsets.UTF_8))
      val output = new ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(new PrintStream(output)) {
          assert(askHowManyPlayers() == 3)
        }
      }
      val expectedOutput = "How many players? Please enter a number between 2 and 4.\nHow many players?"
      assert(output.toString(StandardCharsets.UTF_8.name()).trim == expectedOutput)
    }
  }
}