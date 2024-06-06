package de.htwg.se.skullking.view.gui.components

import scalafx.Includes.*
import scalafx.scene.control.TextField
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.text.Text

class InputField extends TextField {
  style = "-fx-font-size: 40px;"
    + "-fx-border-radius: 20px;"
    + "-fx-background-radius: 20px;"
    + "-fx-background-color: rgba(213, 213, 213, 0.31);"
    + "-fx-border-color: rgba(48, 33, 26, 0.71);"
    + "-fx-border-width: 2px;"
    + "-fx-text-fill: #fff;"
    + "-fx-prompt-text-fill: #fff;"
    + "-fx-overflow: hidden;"

  val insetShadow = new DropShadow {
    offsetX = 4
    offsetY = 4
    color = "rgba(0, 0, 0, 0.25)"
    input = new DropShadow {
      offsetX = -4
      offsetY = -4
      color = "rgba(0, 0, 0, 0.25)"
    }
  }

  effect = insetShadow
}
