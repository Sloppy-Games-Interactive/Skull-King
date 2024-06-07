package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{GameButton, PlayerListRow}
import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.view.gui.components.gameScene.{AddPredictionPanel, PauseMenuPanel}
import de.htwg.se.skullking.view.gui.components.modal.Overlay
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, Priority, Region, StackPane, VBox}
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
) extends Scene(windowWidth, windowHeight) {

  private val quitGameBtn: Button = new GameButton(medium) {
    text = "Quit Game"
    onAction = onClickQuitBtn
  }

  var modalBox: AddPredictionPanel = AddPredictionPanel(controller)
  var PauseMenu: PauseMenuPanel = PauseMenuPanel(controller, () => pauseMenuOverlay.toggleModal())

  val overlay = new Overlay(windowWidth, windowHeight, () => sceneContent, modalBox)
  val pauseMenuOverlay = new Overlay(windowWidth, windowHeight, () => sceneContent, PauseMenu)

  var someBtn: Button = new Button {
    text = "Some Button"
    onAction = () => overlay.toggleModal()
  }

  val leftColumn: VBox = new VBox {
    val title = new Label("Ye Olde Crew")
    title.getStyleClass.add("title")
    children = Seq(
      title,
      new PlayerListRow("Player1", 0),
      new PlayerListRow("Player1", 1),
      new PlayerListRow("Player1", 2),

    )
  }
  leftColumn.spacing = 10

  val titleAndButton = new HBox {
    children = Seq(
      leftColumn,
      new Region {
        // This region will expand and push the buttons to the edges
        hgrow = javafx.scene.layout.Priority.ALWAYS
      },

      new GameButton(medium) {
        text = "Menu"
        onAction = () => pauseMenuOverlay.toggleModal()
      },
    )
  }
  titleAndButton.getStyleClass.add("title-and-button")

  val sceneContent: StackPane = new StackPane {
    children = Seq(
      new ImageView {
        image = new Image("/images/background/tavern.jpeg")
        fitWidth = windowWidth
        fitHeight = windowHeight
      },
      new VBox {
        children = Seq(
          titleAndButton,
          new HBox {
            children = Seq(quitGameBtn)
          },
          new HBox {
            children = Seq(someBtn)
          }
        )
      },
    )
  }

  root = new StackPane {
    vgrow = Priority.Always
    hgrow = Priority.Always
    children = Seq(
      sceneContent,
      overlay.imageView,
      overlay.modal,
      pauseMenuOverlay.imageView,
      pauseMenuOverlay.modal,
    )
  }

  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.gameSceneCss)
  this.getStylesheets.add(Styles.playerListRowCss)
}