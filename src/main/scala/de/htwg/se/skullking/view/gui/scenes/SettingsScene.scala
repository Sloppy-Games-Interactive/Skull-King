package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.GameButton
import scalafx.Includes.*
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, VBox}

case class SettingsScene(
  controller: IController,
  windowWidth: Double,
  windowHeight: Double,
  onClickBackButton: () => Unit = () => println("Back"),
) extends Scene(windowWidth, windowHeight
) {
  private val backBtn: Button = new GameButton(medium) {
    text = "Back"
    onAction = onClickBackButton
  }

  root = new VBox {
    children = Seq(
      new HBox(backBtn)
    )
  }

  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.settingsSceneCss)
}
