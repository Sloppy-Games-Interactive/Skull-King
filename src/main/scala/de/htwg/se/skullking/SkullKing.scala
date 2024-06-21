package de.htwg.se.skullking

import de.htwg.se.skullking.modules.Default.given
import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.view.tui.Tui

import scala.io.StdIn.readLine
import de.htwg.se.skullking.view.gui.Gui

object SkullKing {
  private val controller: IController = summon[IController]
  private val gui: Gui = Gui(controller)
  private val tui: Tui = Tui(controller)

  def main(args: Array[String]): Unit = {
    new Thread(() => {
      gui.main(Array.empty)
    }).start()

    var input: String = ""

    while (input != ":quit") {
      input = readLine()
      tui.processInputLine(input)
    }
  }
}
