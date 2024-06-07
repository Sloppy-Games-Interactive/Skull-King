package de.htwg.se.skullking.view.gui.components

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, HBox, Pane, Region}
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.effect.InnerShadow
import scalafx.scene.layout.Priority.Always

class PlayerListRow (playerName: String, playerScore: Int = 0) extends HBox{
  val name: String = playerName
  val score: Int = playerScore

  alignment = Pos.CenterLeft
  private val imageView = new ImageView("/images/icon.png") {
    fitHeight = 110
    fitWidth = 110
  }

  imageView.getStyleClass.add("player-icon")


  private val imagePane = new Pane {
    children = Seq(imageView)
  }

  private val nameLabel: Label = new Label(name)
  nameLabel.getStyleClass.add("name-label")
  nameLabel.setPadding(Insets(0, 10, 0, 10))

  private val scoreLabel: Label = new Label(score.toString)
  scoreLabel.getStyleClass.add("score-label")


  // Main layout of the row (HBox)
  val mainLayout = children = Seq(
    imagePane,
    nameLabel,
    new Region {
      hgrow = Always
    },
    scoreLabel
  )
  this.getStyleClass.add("player-row")
}