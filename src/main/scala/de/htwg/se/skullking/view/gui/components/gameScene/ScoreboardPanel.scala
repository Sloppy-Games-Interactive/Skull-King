package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import scalafx.scene.layout.VBox

import scala.collection.immutable.List

class ScoreboardPanel(
  controller: Controller,
  height: Int = 3000,
  width: Int = 1191
) extends VBox with Observer {
  controller.add(this)

  def update(event: ObservableEvent): Unit = {
    
  }
}
