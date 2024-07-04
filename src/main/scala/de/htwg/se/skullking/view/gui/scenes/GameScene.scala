package de.htwg.se.skullking.view.gui.scenes

import de.htwg.se.skullking.controller.ControllerComponent.{ControllerEvents, IController}
import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.gameScene.*
import de.htwg.se.skullking.view.gui.components.modal.Overlay
import de.htwg.se.skullking.view.gui.components.{GameButton, PlayerListRow}
import scalafx.Includes.*
import scalafx.animation.PauseTransition
import scalafx.application.Platform
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.*
import scalafx.util.Duration

case class GameScene(
  controller: IController,
  windowWidth: Double,
  windowHeight: Double,
  onClickQuitBtn: () => Unit = () => println("Quit Game"),
) extends Scene(windowWidth, windowHeight) with Observer {
  controller.add(this)

  private def updatePlayerList(): Unit = {
    val finishedTricks = controller.state.tricks.filter(t => t.stack.length == controller.state.players.length)
    leftColumn.children = controller.state.players.map(player => new PlayerListRow(player, finishedTricks)) ++ playerList
  }

  def displayRoundNumberModal(): Unit = {
    roundNumberModal.text = roundText()
    roundNumberOverlay.openModal(fadeIn = true)

    val pause = new PauseTransition(Duration(1500))
    pause.onFinished = _ => roundNumberOverlay.closeModal(fadeOut = true)
    pause.play()
  }

  def displayTrickWinnerModal(): Unit = {
    val mostRecentCompleteTrick = controller.state.lastTrickWinner
    if (mostRecentCompleteTrick.isDefined) {
      trickCompleteModal.text = trickCompleteText(mostRecentCompleteTrick.get)
      trickCompleteOverlay.openModal(fadeIn = true)

      val pause = new PauseTransition(Duration(1500))
      pause.onFinished = _ => {
        trickCompleteOverlay.closeModal(fadeOut = true)
      }
      pause.play()
    }
  }

  def update(event: ObservableEvent): Unit = {
    Platform.runLater {
      event match {
        case ControllerEvents.PromptPrediction => {
          val noPlayerHasPredicted = controller.state.players.forall(p => p.prediction.isEmpty)
          if (controller.state.tricks.isEmpty && noPlayerHasPredicted) {
            if (trickCompleteOverlay.modal.visible.value) {
              trickCompleteOverlay.onCloseFinish = () => {
                displayRoundNumberModal()
                trickCompleteOverlay.onCloseFinish = () => ()
              }
            } else {
              displayRoundNumberModal()
            }
          }
          if (!predictionOverlay.modal.visible.value) predictionOverlay.openModal(fadeIn = true)
        }
        case ControllerEvents.PredictionSet if (controller.state.activePlayer.get.prediction.isDefined) => predictionOverlay.closeModal(fadeOut = true)
        case ControllerEvents.PlayerAdded => updatePlayerList()
        case ControllerEvents.PromptCardPlay => updatePlayerList()
        case ControllerEvents.CardPlayed => {
          updatePlayerList()

          // no predictions == new round
          val noPlayerHasPredicted = controller.state.players.forall(p => p.prediction.isEmpty)
          val activeTrick = controller.state.activeTrick
          if (noPlayerHasPredicted || (activeTrick.isDefined && activeTrick.get.stack.isEmpty)) {
            displayTrickWinnerModal()
          }
        }
        case ControllerEvents.NewGame => println("New Game") //TODO: Show Player
        case ControllerEvents.SaveGame => {
          saveNotificationModal.text = "Game saved!"
          saveNotificationOverlay.openModal(fadeIn = true)

          val pause = new PauseTransition(Duration(1500))
          pause.onFinished = _ => saveNotificationOverlay.closeModal(fadeOut = true)
          pause.play()
        }
        case ControllerEvents.LoadGame => {
          Platform.runLater {
            saveNotificationModal.text = "Game loaded!"
            saveNotificationOverlay.openModal(fadeIn = true)

            val pause = new PauseTransition(Duration(1500))
            pause.onFinished = _ => saveNotificationOverlay.closeModal(fadeOut = true)
            pause.play()
          }
        }
        case _ =>
      }
    }
  }

  private def roundText(): String = s"Round ${controller.state.round}"
  val roundNumberModal = new Label(roundText()) {
    styleClass.add("round-number")
  }
  val roundNumberOverlay = new Overlay(
    windowWidth,
    windowHeight,
    () => sceneContent,
    roundNumberModal
  )

  private def trickCompleteText(player: IPlayer): String = s"Trick Complete, Player ${player.name} won!"
  val trickCompleteModal = new Label("") {
    styleClass.add("trick-complete")
  }
  val trickCompleteOverlay = new Overlay(
    windowWidth,
    windowHeight,
    () => sceneContent,
    trickCompleteModal
  )

  val saveNotificationModal = new Label("") {
    styleClass.add("save-notification")
  }
  val saveNotificationOverlay = new Overlay(
    windowWidth,
    windowHeight,
    () => sceneContent,
    saveNotificationModal
  )

  var predictionModalBox: AddPredictionPanel = AddPredictionPanel(controller)
  var PauseMenu: PauseMenuPanel = PauseMenuPanel(controller, () => pauseMenuOverlay.toggleModal(), onClickQuitBtn, () => scoreboardOverlay.openModal())
  var playCardModal: PlayCardPanel = PlayCardPanel(controller, () => playCardOverlay.closeModal(), controller.playCard)
  val scoreboardModal: ScoreboardPanel = ScoreboardPanel(controller, () => scoreboardOverlay.toggleModal())

  val predictionOverlay = new Overlay(windowWidth, windowHeight, () => sceneContent, predictionModalBox)
  val pauseMenuOverlay = new Overlay(windowWidth, windowHeight, () => sceneContent, PauseMenu)
  val playCardOverlay = new Overlay(windowWidth, windowHeight, () => sceneContent, playCardModal)
  val scoreboardOverlay = new Overlay(windowWidth, windowHeight, () => sceneContent, scoreboardModal)

  var playerList: Seq[PlayerListRow] = Seq()
  val leftColumn: VBox = new VBox {
    children = Seq()
  }
  leftColumn.spacing = 10

  val titleAndButton = new HBox {
    children = Seq(
      leftColumn,
      new Region {
        // This region will expand and push the buttons to the edges
        hgrow = Priority.Always
      },
      new TrickStack(controller) {
        alignment = Pos.Center
      },
      new GameButton(medium) {
        text = "Menu"
        onAction = () => pauseMenuOverlay.toggleModal()
      },
    )
  }
  titleAndButton.getStyleClass.add("title-and-button")
  
  def handCardClicked(card: ICard): Unit = {
    controller.state.activePlayer match {
      case Some(player) => {
        playCardModal.openWithCard(card, player)
        playCardOverlay.openModal()
      }
      case None => println("No active player")
    }
  }

  val sceneContent: StackPane = new StackPane {
    alignment = Pos.BottomCenter
    children = Seq(
      new ImageView {
        image = new Image("/images/background/tavern.jpeg")
        fitWidth = windowWidth
        fitHeight = windowHeight
      },
      new VBox {
        children = Seq(
          titleAndButton,
        )
      },
      new VBox {
        style = "-fx-max-height: 400;"
        alignment = Pos.BottomCenter
        children = Seq(new PlayerHand(controller, handCardClicked))
      }
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
      playCardOverlay.imageView,
      playCardOverlay.modal,
      scoreboardOverlay.imageView,
      scoreboardOverlay.modal,
      roundNumberOverlay.imageView,
      roundNumberOverlay.modal,
      trickCompleteOverlay.imageView,
      trickCompleteOverlay.modal,
      saveNotificationOverlay.imageView,
      saveNotificationOverlay.modal,
    )
  }

  this.getStylesheets.add(Styles.mainCss)
  this.getStylesheets.add(Styles.gameSceneCss)
  this.getStylesheets.add(Styles.playerListRowCss)
}