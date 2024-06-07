package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.{large, medium, small}
import de.htwg.se.skullking.view.gui.components.GameButton
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, VBox}
import scalafx.Includes.*
import scalafx.scene.text.Text


case class TitleScene(
  controller: Controller,
  windowWidth: Double,
  windowHeight: Double,
  onClickPlayButton: () => Unit = () => println("Play"),
  onClickSettingsButton: () => Unit = () => println("Settings"),
  onClickQuitButton: () => Unit = () => println("Quit")
) extends Scene(windowWidth, windowHeight) {

  private val title: Text = new Text {
    text = "Skull King"
  }

  private val playGameBtn: Button = new GameButton(large) {
    text = "Play"
    onAction = () => {
      controller.newGame
      onClickPlayButton()
    }
  }
  playGameBtn.setId("large-button")

  private val settingsBtn: Button = new GameButton(medium) {
    text = "Settings"
    onAction = onClickSettingsButton
  }

  private val quitBtn: Button = new GameButton(medium) {
    text = "Quit"
    onAction = onClickQuitButton
  }

  root = new VBox {
    children = Seq(
      title,
      playGameBtn,
      new HBox {
        children = Seq(
          settingsBtn,
          quitBtn,
        )
      }
    )
  }
  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.titleSceneCss)


}