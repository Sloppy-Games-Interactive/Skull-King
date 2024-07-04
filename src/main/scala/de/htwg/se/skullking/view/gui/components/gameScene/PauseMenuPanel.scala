package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.modal.Overlay
import de.htwg.se.skullking.view.gui.components.{BtnSize, GameButton, InputField}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.text.{Font, Text}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.Includes.*

class PauseMenuPanel(
  controller: IController,
  toggleClick: () => Unit = () => println("toggle"),
  onClickQuitBtn: () => Unit = () => println("Quit Game"),
  onClickScoreboardBtn: () => Unit = () => println("Scoreboard")
) extends VBox{

  alignment = Pos.TopRight

  children = Seq(

    new GameButton(medium) {
      text = "Menu"
      onAction = () => toggleClick()
    },

//    new GameButton(medium) {
//      text = "Scoreboard"
//      disable = true
//      onAction = () => {
//        toggleClick()
//        onClickScoreboardBtn()
//      }
//    },
    new GameButton(medium) {
      text = "Undo"
      onAction = () => {
        toggleClick()
        controller.undo
      }
    },
    new GameButton(medium) {
      text = "Redo"
      onAction = () => {
        toggleClick()
        controller.redo
      }
    },
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
