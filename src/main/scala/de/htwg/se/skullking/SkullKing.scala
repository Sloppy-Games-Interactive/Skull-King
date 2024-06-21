package de.htwg.se.skullking

import com.google.inject.{Guice, Injector}
import de.htwg.se.skullking.modules.DefaultModule
//import de.htwg.se.skullking.modules.Default.given

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.view.tui.Tui

import scala.io.StdIn.readLine
import de.htwg.se.skullking.view.gui.Gui

object SkullKing {
  val injector: Injector = Guice.createInjector(DefaultModule())

  private val controller: IController = injector.getInstance(classOf[IController])
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
