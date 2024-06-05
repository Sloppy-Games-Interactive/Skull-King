import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.view.tui.Tui
import scala.io.StdIn.readLine

@main def run(): Unit = {
  val controller = Controller()
  val tui = Tui(controller)

  var input: String = ""

  while (input != ":quit") {
    input = readLine()
    tui.processInputLine(input)
  }
}
