import de.htwg.se.skullking.controller.ControllerComponent.Controller
import de.htwg.se.skullking.view.tui.Tui

import scala.io.StdIn.readLine
import de.htwg.se.skullking.view.gui.Gui

@main def run(): Unit = {
  val controller = Controller()
  val gui = new Gui(controller)

  new Thread(() => {
    gui.main(Array.empty)
  }).start()

  val tui = Tui(controller)

  var input: String = ""
  

  while (input != ":quit") {
    input = readLine()
    tui.processInputLine(input)
  }
}
