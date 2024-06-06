package de.htwg.se.skullking.view.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.Includes.*
import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.view.gui.Styles

case class TitleScene(
  controller: Controller,
  windowWidth: Double,
  windowHeight: Double,
  onClickPlayButton: () => Unit = () => println("Play"),
  onClickSettingsButton: () => Unit = () => println("Settings"),
  onClickQuitButton: () => Unit = () => println("Quit")
) extends Scene(windowWidth, windowHeight) {

  private val playGameBtn: Button = new Button {
    text = "PLAY"
    onAction = onClickPlayButton
  }

  private val settingsBtn: Button = new Button {
    text = "Settings"
    onAction = onClickSettingsButton
  }

  private val quitBtn: Button = new Button {
    text = "Quit"
    onAction = onClickQuitButton
  }

  root = new VBox {
    children = Seq(
        playGameBtn,
        settingsBtn,
        quitBtn
    )
  }
  this.getStylesheets.add(Styles.mainCss)

}