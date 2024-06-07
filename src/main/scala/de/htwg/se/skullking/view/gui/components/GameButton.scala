package de.htwg.se.skullking.view.gui.components

import de.htwg.se.skullking.view.gui.Styles
import scalafx.scene.control.Button

enum BtnSize:
  case micro, small, medium, large

class GameButton(size: BtnSize) extends Button {
  size match {
    case BtnSize.micro => this.setId("micro-button")
    case BtnSize.small => this.setId("small-button")
    case BtnSize.medium => this.setId("medium-button")
    case BtnSize.large => this.setId("large-button")
  }
  this.getStylesheets.add(Styles.gameButtonCss)

}
