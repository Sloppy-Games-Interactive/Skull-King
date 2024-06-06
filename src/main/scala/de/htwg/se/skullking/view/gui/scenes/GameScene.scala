package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.controller.Controller
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.Includes.*

case class GameScene(
  controller: Controller,
  windowWidth: Double,
  windowHeight: Double,
  onClickQuitBtn: () => Unit = () => println("Quit Game"),
) extends Scene(windowWidth, windowHeight
) {

  private val quitGameBtn: Button = new Button {
    text = "Quit Game"
    onAction = onClickQuitBtn
  }

  root = new VBox {
    children = Seq(
      quitGameBtn
    )
  }
}