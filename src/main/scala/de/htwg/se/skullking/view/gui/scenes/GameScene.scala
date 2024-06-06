package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{GameButton, PlayerListRow}
import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.view.gui.components.gameScene.AddPredictionPanel
import de.htwg.se.skullking.view.gui.components.modal.Overlay
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, Pane, Priority, StackPane, VBox}
import scalafx.Includes.*
import scalafx.animation.Timeline
import scalafx.geometry.Pos
import scalafx.scene.effect.{BlendMode, BoxBlur, GaussianBlur}
import scalafx.scene.image.{Image, ImageView, WritableImage}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

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

  var modalBox = AddPredictionPanel(controller)

  val overlay = new Overlay(windowWidth, windowHeight, () => sceneContent, modalBox)

  var someBtn: Button = new Button {
    text = "Some Button"
    onAction = () => overlay.toggleModal()
  }

  val sceneContent: StackPane = new StackPane {
    children = Seq(
      new ImageView {
        image = new Image("/images/background/tavern.jpeg")
        fitWidth = windowWidth
        fitHeight = windowHeight
      },
      new VBox {
        children = Seq(
          new HBox {
            children = Seq(
              new PlayerListRow("Player 1", 0)
            )
          },
          new HBox {
            children = Seq(quitGameBtn)
          },
          new HBox {
            children = Seq(someBtn)
          }
        )
      }
    )
  }

  root = new StackPane {
    vgrow = Priority.Always
    hgrow = Priority.Always
    children = Seq(
      sceneContent,
      overlay.imageView,
      overlay.modal,
    )
  }

  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.gameSceneCss)
  this.getStylesheets.add(Styles.playerListRowCss)
}