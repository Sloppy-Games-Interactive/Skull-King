package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.{BtnSize, GameButton, InputField}
import de.htwg.se.skullking.view.gui.components.preGame.AddPlayersPanel
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, VBox}
import scalafx.Includes.*
import scalafx.event.ActionEvent
import scalafx.geometry.Insets


case class PreGameScene(
  controller: IController,
  windowWidth: Double,
  windowHeight: Double,
  onClickStartGameButton: () => Unit = () => println("Start Game"),
) extends Scene(windowWidth, windowHeight
) {
  private val addPlayersPanel: AddPlayersPanel = AddPlayersPanel(controller)

  val startGameBtn: GameButton = new GameButton(BtnSize.medium) {
    text = "Start Game"
    onAction = (_: ActionEvent) => {
      val playerNames = addPlayersPanel.getPlayerNames
      if (playerNames.length >= 2) {
        controller.setPlayerLimit(playerNames.length)
        playerNames.foreach(controller.addPlayer)
        addPlayersPanel.resetPlayerNames
        onClickStartGameButton()
      }
      // TODO more error handling
    }
  }

  onKeyPressed = (event) => {
    if (event.code.toString == "ENTER") {
      startGameBtn.fire()
    }
  }

  root = new VBox {
    style = "-fx-spacing: -100;"
    padding = Insets(0, 0, 40, 0)
    children = Seq(
      new HBox {
        children = Seq(
          addPlayersPanel
        )
      },
      new HBox {
        children = Seq(
          startGameBtn
        )
      }
    )
  }

  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.preGameSceneCss)
}
