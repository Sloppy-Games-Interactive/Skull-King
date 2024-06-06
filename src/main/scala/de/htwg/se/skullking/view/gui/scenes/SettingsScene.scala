package de.htwg.se.skullking.view.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.Includes._

case class SettingsScene(
  windowWidth: Double,
  windowHeight: Double,
  onClickBackButton: () => Unit = () => println("Back"),
) extends Scene(windowWidth, windowHeight
) {
  private val backBtn: Button = new Button {
    text = "Back"
    onAction = onClickBackButton
  }

  root = new VBox {
    children = Seq(
      backBtn
    )
  }
}
