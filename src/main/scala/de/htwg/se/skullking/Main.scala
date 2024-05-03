import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.view.Tui
import scala.io.StdIn.readLine

@main def run(): Unit = {
  val controller = Controller()
  val tui = Tui(controller)

  var input: String = ""

  while (input != "q") {
    input = readLine()
    tui.processInputLine(input)
  }
}
