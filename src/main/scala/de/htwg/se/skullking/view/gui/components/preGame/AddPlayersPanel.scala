package de.htwg.se.skullking.view.gui.components.preGame

import de.htwg.se.skullking.controller.ControllerComponent.IController
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

class AddPlayersPanel(controller: IController) extends VBox {
  prefHeight = 4000
  prefWidth = 1191
  fillWidth = false
  alignment = Pos.Center
  styleClass.add("panel")

  val title: Text = new Text {
    text = "Add Players"
    styleClass.add("title")
    fill = Color.White
  }

  private val players = 4
  private val playerInputFields: Seq[InputField] = for (player <- 1 to players) yield new InputField {
    prefWidth = 577
    prefHeight = 70
    promptText = "Enter Name"
  }
  private val playerFields = for (player <- 1 to players) yield new HBox {
    style = "-fx-spacing: 70;"
    children = Seq(
      new Text {
        text = s"Player ${player}"
        style = "-fx-font-size: 64px;"
        fill = Color.White
        prefHeight = 76
      },
      playerInputFields(player - 1)
    )
  }

  private val inputFields: VBox = new VBox {
    style = "-fx-spacing: 45;"
    alignment = Pos.Center
    children = playerFields
  }

  def getPlayerNames: Seq[String] = playerInputFields.map(_.text.value).filterNot(_.isEmpty)
  
  def resetPlayerNames: Unit = playerInputFields.foreach(_.text.value = "")

  children = Seq(
    title,
    inputFields
  )
  
  this.getStylesheets.add(Styles.panelCss)
}
