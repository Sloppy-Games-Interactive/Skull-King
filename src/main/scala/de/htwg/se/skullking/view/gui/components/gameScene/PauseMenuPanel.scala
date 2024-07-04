package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{BtnSize, GameButton}
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.Pos
import scalafx.scene.layout.VBox

class PauseMenuPanel(
  controller: IController,
  toggleClick: () => Unit = () => println("toggle"),
  onClickQuitBtn: () => Unit = () => println("Quit Game"),
  onClickScoreboardBtn: () => Unit = () => println("Scoreboard")
) extends VBox with Observer {
  controller.add(this)

  def update(event: ObservableEvent): Unit = {
    Platform.runLater {
      event match {
        case _ => {
          undoBtn.disable = !controller.undoManager.canUndo
          redoBtn.disable = !controller.undoManager.canRedo
        }
      }
    }
  }

  alignment = Pos.TopRight

  val undoBtn = new GameButton(medium) {
    text = "Undo"
    disable = !controller.undoManager.canUndo
    onAction = () => {
      toggleClick()
      controller.undo
    }
  }

  val redoBtn = new GameButton(medium) {
    text = "Redo"
    disable = !controller.undoManager.canRedo
    onAction = () => {
      toggleClick()
      controller.redo
    }
  }

  children = Seq(

    new GameButton(medium) {
      text = "Menu"
      onAction = () => toggleClick()
    },

    undoBtn,
    redoBtn,
    new GameButton(medium) {
      text = "Save Game"
      onAction = () => {
        toggleClick()
        controller.saveGame
      }
    },
    new GameButton(medium) {
      text = "Load Game"
      onAction = () => {
        toggleClick()
        controller.loadGame
      }
    },
    new GameButton(medium) {
      text = "Quit"
      onAction = () => {
        toggleClick()
        onClickQuitBtn()
      }
    },
    
  )

  this.getStyleClass.add("pause-menu-panel")
  this.getStylesheets.add(Styles.PauseMenuPanelCss)
}
