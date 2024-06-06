package de.htwg.se.skullking.view.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.Includes._


case class PreGameScene(
  windowWidth: Double,
  windowHeight: Double,
  onClickStartGameButton: () => Unit = () => println("Start Game"),
) extends Scene(windowWidth, windowHeight
) {

  private val startGameBtn: Button = new Button {
    text = "Start Game"
    onAction = onClickStartGameButton
  }

  root = new VBox {
    children = Seq(
        startGameBtn
    )
  }
}
