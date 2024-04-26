import scala.io.StdIn.readLine
import controller.Controller
import view.Tui

@main def run(): Unit = {
  val controller = Controller()
  val tui = Tui(controller)
  var state = controller.newGame

  var input: String = ""

  while (input != "q") {
    println(state.getStatusAsTable)
    input = readLine()
    state = tui.processInputLine(input, state)
  }
}
