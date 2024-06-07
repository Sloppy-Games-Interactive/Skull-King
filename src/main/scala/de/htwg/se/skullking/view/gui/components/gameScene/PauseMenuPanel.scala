package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.Controller
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
  controller: Controller,
  toggleClick: () => Unit = () => println("toggle"),
  onClickQuitBtn: () => Unit = () => println("Quit Game"),
) extends VBox{

  val modalBox: ScoreboardPanel = new ScoreboardPanel(controller)
  val overlay: Overlay = new Overlay(1191, 1440, () => this, modalBox)

  alignment = Pos.TopRight

  children = Seq(

    new GameButton(medium) {
      text = "Menu"
      onAction = () => toggleClick()
    },

    new GameButton(medium) {
      text = "Scoreboard"
      onAction = () => overlay.toggleModal()
    },
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
      text = "Quit"
      onAction = () => {
        toggleClick()
        onClickQuitBtn()
      }
    },

    // Breaks the Menu modal
//    overlay.imageView,
//    overlay.modal
  )

  this.getStyleClass.add("pause-menu-panel")
  this.getStylesheets.add(Styles.PauseMenuPanelCss)
}
