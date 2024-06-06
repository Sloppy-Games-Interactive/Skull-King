package de.htwg.se.skullking.view.gui.components

import scalafx.scene.control.Button

class GameButton extends Button{

  style = "-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #ffffff; -fx-border-width: 2px;"

  onMouseEntered = _ => {
    style = "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-width: 2px;"
  }

  onMouseExited = _ => {
    style = "-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #ffffff; -fx-border-width: 2px;"
  }

  onMousePressed = _ => {
    style = "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-width: 2px;"
  }

  onMouseReleased = _ => {
    style = "-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #ffffff; -fx-border-width: 2px;"
  }
}
