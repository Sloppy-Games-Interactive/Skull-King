package de.htwg.se.skullking.view.gui

import de.htwg.se.skullking.controller.{Controller, ControllerEvents}
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.scenes.{GameScene, PreGameScene, SettingsScene, TitleScene}

import scala.compiletime.uninitialized
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.text.Font
import scalafx.application.Platform
import scalafx.application.JFXApp3.PrimaryStage

object Styles {
  val mainCss: String = getClass.getResource("/styles/main.css").toExternalForm
  val gameSceneCss: String = getClass.getResource("/styles/scenes/gameScene.css").toExternalForm
  val titleSceneCss: String = getClass.getResource("/styles/scenes/titleScene.css").toExternalForm
  val preGameSceneCss: String = getClass.getResource("/styles/scenes/preGameScene.css").toExternalForm
  val settingsSceneCss: String = getClass.getResource("/styles/scenes/settingsScene.css").toExternalForm
  
  // components
  val gameButtonCss: String = getClass.getResource("/styles/components/gameButton.css").toExternalForm
  val playerListRowCss: String = getClass.getResource("/styles/components/playerListRow.css").toExternalForm
  val panelCss: String = getClass.getResource("/styles/components/panel.css").toExternalForm
}

class Gui(controller: Controller) extends JFXApp3 with Observer {
  controller.add(this)

  private var titleScene: TitleScene = uninitialized
  private var preGameScene: PreGameScene = uninitialized
  private var settingsScene: SettingsScene = uninitialized
  private var gameScene: GameScene = uninitialized


  private val windowWidth = 1440
  private val windowHeight = 1024

  private val minWindowWidth = 500
  private val minWindowHeight = 300

  Font.loadFont(getClass.getResourceAsStream("/fonts/Pieces_of_Eight.ttf"), 50)

  override def start(): Unit = {
    titleScene = TitleScene(
      controller = controller,
      windowHeight = windowHeight,
      windowWidth = windowWidth,
      onClickPlayButton = () => stage.setScene(preGameScene),
      onClickSettingsButton = () => stage.setScene(settingsScene),
      onClickQuitButton = () => controller.quit
    )

    settingsScene = SettingsScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      onClickBackButton = () => stage.setScene(titleScene)
    )

    preGameScene = PreGameScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      onClickStartGameButton = () => stage.setScene(gameScene),
    )

    gameScene = GameScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      onClickQuitBtn = () => stage.setScene(titleScene)
    )


    stage = new JFXApp3.PrimaryStage {
      resizable = false
      title = "Skull King - Card Game"
      scene = titleScene
      minWidth = minWindowWidth
      minHeight = minWindowHeight
      icons. += (new Image(getClass.getResourceAsStream("/images/icon.png")))

      // override close button function
      onCloseRequest = () => {
        controller.quit
      }
    }
  }

  override def update(event: ObservableEvent): Unit = {
    event match {
      case ControllerEvents.Quit => Platform.exit()
      case _ => println("Update")
    }
  }

}

