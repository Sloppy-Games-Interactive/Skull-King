package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{BtnSize, CardPane, CardSize, GameButton, InputField}
import scalafx.scene.layout.{FlowPane, HBox, StackPane, VBox}
import scalafx.scene.text.{Font, Text}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.Includes.*

class AddPredictionPanel(
  controller: Controller,
  height: Int = 3000,
  width: Int = 1191
) extends VBox with Observer {
  controller.add(this)

  prefHeight = height
  prefWidth = width
  fillWidth = false
  alignment = Pos.Center
  styleClass.add("panel")

  val title: Text = new Text {
    text = "Your Hand:"
    styleClass.add("title")
    fill = Color.White
  }

  var activeHand = new HBox {
    children = Seq()
  }

  var predictionButtons = new FlowPane {
    children = Seq()
    hgap = 10
    vgap = 6
    alignment = Pos.Center
  }

  var selectedPrediction: Int = -1

  def update(event: ObservableEvent): Unit = {
    Platform.runLater {
      val handCards = controller.state.activePlayer match {
        case Some(player) => player.hand.cards
        case None => List()
      }

      val cardMargin = handCards.size match {
        case size if size < 5 => 30
        case size if size >= 5 && size <= 7 => -50
        case size if size > 7 => -100
      }

      activeHand.children = handCards.map(card => new CardPane(card, CardSize.Small) {
        padding = Insets(0, cardMargin, 0, 0)
        onMouseClicked = _ => {
          this.toFront()
        }
      })

      var groupedPredictions: List[List[Int]] = (0 to handCards.size).grouped(8).toList.map(_.toList)

      // If the last group has only one element, merge it with the previous group
      if (groupedPredictions.nonEmpty && groupedPredictions.last.size == 1 && groupedPredictions.size > 1) {
        val lastTwoGroupsMerged = groupedPredictions.takeRight(2).flatten
        groupedPredictions = groupedPredictions.dropRight(2) :+ lastTwoGroupsMerged
      }

      predictionButtons.children = groupedPredictions.map { predictions =>
        new HBox {
          children = predictions.map(prediction => new GameButton(BtnSize.micro) {
            text = prediction.toString
            onAction = (_: ActionEvent) => {
              predictionButtons.children.foreach(_.asInstanceOf[HBox].children.foreach(_.getStyleClass.removeAll("selected")))
              this.getStyleClass.add("selected")
              selectedPrediction = prediction
            }
          })
        }
      }
    }
  }

  var confirmButton = new GameButton(medium) {
    text = "Confirm"
    onAction = (_: ActionEvent) => {
      controller.state.activePlayer match {
        case Some(player) => controller.setPrediction(player, selectedPrediction)
        case None => println("No active player")
      }
    }
  }

  private val panelBody = new VBox {
    style = "-fx-spacing: 45;"
    alignment = Pos.Center
    children = Seq(
      activeHand,
      predictionButtons,
    )
  }

  private val panelContent = new VBox {
    style = "-fx-spacing: 45;"
    alignment = Pos.Center
    children = Seq(
      title,
      panelBody
    )
  }

  private val confirmButtonBox = new HBox {
    alignment = Pos.Center
    children = Seq(
      confirmButton
    )
  }

  children = Seq(
    new VBox {
      style = "-fx-spacing: 100;"
      alignment = Pos.Center
      children = Seq(
        panelContent,
        confirmButtonBox
      )
    }
  )

  this.getStylesheets.add(Styles.panelCss)
}