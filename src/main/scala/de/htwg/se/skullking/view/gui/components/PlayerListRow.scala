package de.htwg.se.skullking.view.gui.components

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{HBox, Pane}
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.layout.Background
import scalafx.scene.layout.BackgroundFill
import scalafx.scene.layout.CornerRadii
import scalafx.geometry.Insets
import scalafx.scene.control.Label

class PlayerListRow (playerName: String, playerScore: Int = 0) extends HBox{
  val name: String = playerName
  val score: Int = playerScore

  private val imageView = new ImageView("/images/icon.png") {
    fitHeight = 110
    fitWidth = 110
  }

  imageView.clip = new Rectangle {
    width = imageView.fitWidth.value
    height = imageView.fitHeight.value
    arcWidth = 100
    arcHeight = 100
  }

  private val imagePane = new Pane {
    children = Seq(imageView)
    style = "-fx-border-color: #B4801C; -fx-border-width: 3; -fx-border-radius: 100;"
  }

  private val nameLabel: Label = new Label(name)
  nameLabel.setId("name-label")

  private val scoreLabel: Label = new Label(score.toString)
  scoreLabel.setId("score-label")

  children = Seq(
    imagePane,
    nameLabel,
    scoreLabel
  )
}