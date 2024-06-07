package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{GameButton, PlayerListRow}
import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.model.card.Card
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.components.gameScene.{AddPredictionPanel, PauseMenuPanel, PlayerHand}
import de.htwg.se.skullking.view.gui.components.modal.Overlay
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, Priority, Region, StackPane, VBox}
import scalafx.Includes.*
import scalafx.animation.Timeline
import scalafx.application.Platform
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
) extends Scene(windowWidth, windowHeight) with Observer {
  controller.add(this)

  def update(event: ObservableEvent): Unit = {
    Platform.runLater {
      event match {
        case ControllerEvents.PromptPrediction if (!predictionOverlay.modal.visible.value) => predictionOverlay.openModal()
        case ControllerEvents.PredictionSet if (controller.state.activePlayer.get.prediction.isDefined) => predictionOverlay.closeModal()
        case ControllerEvents.PlayerAdded => leftColumn.children = controller.state.players.map(player => new PlayerListRow(player)) ++ playerList
        case ControllerEvents.PromptCardPlay => leftColumn.children = controller.state.players.map(player => new PlayerListRow(player)) ++ playerList
        case _ =>
      }
    }
  }

//  private val quitGameBtn: Button = new GameButton(medium) {
//    text = "Quit Game"
//    onAction = onClickQuitBtn
//  }

  var predictionModalBox: AddPredictionPanel = AddPredictionPanel(controller)
  var PauseMenu: PauseMenuPanel = PauseMenuPanel(controller, () => pauseMenuOverlay.toggleModal(), onClickQuitBtn)

  val predictionOverlay = new Overlay(windowWidth, windowHeight, () => sceneContent, predictionModalBox)
  val pauseMenuOverlay = new Overlay(windowWidth, windowHeight, () => sceneContent, PauseMenu)

  var playerList: Seq[PlayerListRow] = Seq()
  val leftColumn: VBox = new VBox {
    val title = new Label("Ye Olde Crew")
    title.getStyleClass.add("title")

    children = Seq(
      title,
    )
  }
  leftColumn.spacing = 10

  val titleAndButton = new HBox {
    children = Seq(
      leftColumn,
      new Region {
        // This region will expand and push the buttons to the edges
        hgrow = Priority.Always
      },

      new GameButton(medium) {
        text = "Menu"
        onAction = () => pauseMenuOverlay.toggleModal()
      },
    )
  }
  titleAndButton.getStyleClass.add("title-and-button")
  
  def handCardClicked(card: Card): Unit = {
    controller.state.activePlayer match {
      case Some(player) => controller.playCard(player, card)
      case None => println("No active player")
    }
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
          titleAndButton,
//          new HBox {
//            children = Seq(quitGameBtn)
//          },
          new PlayerHand(controller, handCardClicked),
        )
      },
    )
  }

  root = new StackPane {
    vgrow = Priority.Always
    hgrow = Priority.Always
    children = Seq(
      sceneContent,
      predictionOverlay.imageView,
      predictionOverlay.modal,
      pauseMenuOverlay.imageView,
      pauseMenuOverlay.modal,
    )
  }

  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.gameSceneCss)
  this.getStylesheets.add(Styles.playerListRowCss)
}