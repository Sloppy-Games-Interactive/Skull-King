package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{BtnSize, GameButton, InputField}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.text.{Font, Text}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.Includes.*

class AddPredictionPanel(controller: Controller, height: Int = 3000, width: Int = 1191) extends VBox {
  prefHeight = height
  prefWidth = width
  fillWidth = false
  alignment = Pos.Center
  styleClass.add("panel")

  val title: Text = new Text {
    text = "Set Prediction"
    styleClass.add("title")
    fill = Color.White
  }

  private val players = 4
  private val playerFields = for (player <- 1 to players) yield new HBox {
    style = "-fx-spacing: 70;"
    children = Seq(
      new Text {
        text = s"Player ${player}"
        style = "-fx-font-size: 64px;"
        fill = Color.White
        prefHeight = 76
      }
    )
  }

  private val inputFields: VBox = new VBox {
    style = "-fx-spacing: 45;"
    alignment = Pos.Center
    children = playerFields
  }

  children = Seq(
    title,
    inputFields
  )

  this.getStylesheets.add(Styles.panelCss)
}