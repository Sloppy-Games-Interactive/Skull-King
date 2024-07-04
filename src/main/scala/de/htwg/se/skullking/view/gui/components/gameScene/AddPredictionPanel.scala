package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.{BtnSize, CardEffect, CardPane, CardSize, GameButton, InputField}
import scalafx.scene.layout.{FlowPane, HBox, StackPane, VBox}
import scalafx.scene.text.{Font, Text}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.Includes.*
import scalafx.animation.PauseTransition
import scalafx.util.Duration

class AddPredictionPanel(
  controller: IController,
  panelHeight: Int = 1440,
  panelWidth: Int = 1191
) extends VBox with Observer {
  controller.add(this)

  prefHeight = panelHeight
  prefWidth = panelWidth
  fillWidth = false
  alignment = Pos.Center
  styleClass.add("panel")

  val title: Text = new Text {
    text = "Your Hand:"
    styleClass.add("title")
    fill = Color.White
  }

  var activeHand = new HBox {
    alignment = Pos.Center
    children = Seq()
  }

  var predictionButtons = new FlowPane {
    children = Seq()
    hgap = 10
    vgap = 6
    alignment = Pos.Center
  }

  var selectedPrediction: Int = -1
  var allPredictionButtons: List[GameButton] = List()

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

      activeHand.children = handCards.zipWithIndex.map { case (card, index) =>
        val cardPane = new CardPane(card, CardSize.Small, CardEffect.None, showFaceUp = false) {
          padding = Insets(0, cardMargin, 0, 0)
        }

        // Create a pause transition for the card
        val pause = new PauseTransition(Duration(index * 200)) // 200ms delay between card flips
        pause.onFinished = _ => cardPane.flipFaceUp() // Flip the card over when the pause finishes
        pause.play()

        cardPane
      }

      var groupedPredictions: List[List[Int]] = (0 to handCards.size).grouped(8).toList.map(_.toList)

      // If the last group has only one element, merge it with the previous group
      if (groupedPredictions.nonEmpty && groupedPredictions.last.size == 1 && groupedPredictions.size > 1) {
        val lastTwoGroupsMerged = groupedPredictions.takeRight(2).flatten
        groupedPredictions = groupedPredictions.dropRight(2) :+ lastTwoGroupsMerged
      }

      predictionButtons.children = groupedPredictions.map { (predictions: List[Int]) =>
        new HBox {
          style = "-fx-spacing: 20;"

          val predictionButtonGroup = predictions.map(prediction => new GameButton(BtnSize.micro) {
            text = prediction.toString
            onAction = (_: ActionEvent) => {
              allPredictionButtons.foreach(_.getStyleClass.removeAll("selected"))
              this.getStyleClass.add("selected")
              selectedPrediction = prediction
            }
          })

          allPredictionButtons = allPredictionButtons ++ predictionButtonGroup

          children = predictionButtonGroup
        }
      }
    }
  }

  var confirmButton = new GameButton(medium) {
    text = "Confirm"
    onAction = (_: ActionEvent) => {
      controller.state.activePlayer match {
        case Some(player) => {
          if (selectedPrediction != -1) {
            controller.setPrediction(player, selectedPrediction)
            selectedPrediction = -1
          }
        }
        case None => println("No active player")
      }
    }
  }

  private val panelBody = new VBox {
    style = "-fx-spacing: 30;"
    alignment = Pos.Center
    children = Seq(
      activeHand,
      new Text {
        text = "How many Tricks will you win this round?"
        style = "-fx-font-size: 46;"
        fill = Color.White
      },
      predictionButtons,
    )
  }

  private val panelContent = new VBox {
    style = "-fx-spacing: 30;"
    padding = Insets(180, 0, 0, 0)
    alignment = Pos.TopCenter
    children = Seq(
      title,
      panelBody
    )
  }

  private val confirmButtonBox = new VBox {
    padding = Insets(0, 0, 10, 0)
    alignment = Pos.BottomCenter
    children = Seq(
      confirmButton
    )
  }

  children = Seq(
    new StackPane {
      alignment = Pos.BottomCenter
      prefHeight = panelHeight
      children = Seq(
        new VBox {
          alignment = Pos.TopCenter
          children = Seq(panelContent)
        },
        new VBox {
          style = "-fx-max-height: 100;"
          alignment = Pos.BottomCenter
          children = Seq(confirmButtonBox)
        }
      )
    }
  )

  this.getStylesheets.add(Styles.panelCss)
}