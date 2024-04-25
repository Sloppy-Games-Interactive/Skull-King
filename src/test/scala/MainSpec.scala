import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

class MainSpec extends AnyWordSpec {
  "askHowManyPlayers" should {
    "return the correct number of players" in {
      val input = ByteArrayInputStream("3\n".getBytes(StandardCharsets.UTF_8))
      val output = ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(PrintStream(output)) {
          askHowManyPlayers() should be(3)
        }
      }
      
      output.toString(StandardCharsets.UTF_8.name()).trim should be("How many players?")
    }

    "throw an exception for invalid input" in {
      val input = ByteArrayInputStream("invalid\n".getBytes(StandardCharsets.UTF_8))
      Console.withIn(input) {
        assertThrows[NumberFormatException] {
          askHowManyPlayers()
        }
      }
    }

    "ask again if the number of players is too small" in {
      val input = ByteArrayInputStream("1\n3\n".getBytes(StandardCharsets.UTF_8))
      val output = ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(PrintStream(output)) {
          askHowManyPlayers() should be(3)
        }
      }
      val expectedOutput = "How many players? Please enter a number between 2 and 4.\nHow many players?"
      val outputString = output.toString(StandardCharsets.UTF_8.name())
      // fix string for windows
      outputString.trim().replace("\r\n", "\n") should include(expectedOutput)
    }
  }
}