import de.htwg.se.skullking.modules.Default.given
import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.view.tui.Tui

import scala.io.StdIn.readLine
import de.htwg.se.skullking.view.gui.Gui

@main def run(): Unit = {
  val controller = summon[IController]
  val gui = Gui(controller)
  val tui = Tui(controller)

  new Thread(() => {
    gui.main(Array.empty)
  }).start()

  var input: String = ""

  while (input != ":quit") {
    input = readLine()
    tui.processInputLine(input)
  }
}
