package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{GameButton, PlayerListRow}
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

  private val quitGameBtn: Button = new GameButton(medium) {
    text = "Quit Game"
    onAction = onClickQuitBtn
  }

  root = new VBox {
    children = Seq(
      new PlayerListRow("Player 1", 0),
      quitGameBtn
    )
  }

  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.gameSceneCss)
  this.getStylesheets.add(Styles.playerListRowCss)
}