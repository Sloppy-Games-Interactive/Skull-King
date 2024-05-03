import scala.io.StdIn.readLine
import controller.Controller
import view.Tui

@main def run(): Unit = {
  val controller = Controller()
  val tui = Tui(controller)

  var input: String = ""

  while (input != "q") {
    input = readLine()
    tui.processInputLine(input)
  }
}
