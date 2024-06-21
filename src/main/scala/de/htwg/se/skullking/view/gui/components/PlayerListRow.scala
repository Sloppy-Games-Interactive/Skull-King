package de.htwg.se.skullking.view.gui.components

import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, HBox, Pane, Region}
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.effect.InnerShadow
import scalafx.scene.layout.Priority.Always

class PlayerListRow (player: IPlayer) extends HBox{
  private val name = player.name
  private val score = player.score
  private val isCurrentPlayer = player.active

  alignment = Pos.CenterLeft
  private val imageView = new ImageView("/images/icon.png") {
    fitHeight = 90
    fitWidth = 90
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


  private val activePlayer: Label = new Label("☠") {
    visible = isCurrentPlayer
    effect = new InnerShadow {
      color = Color.White
      radius = 10
      choke = 0.5
    }
  }
  activePlayer.getStyleClass.add("active-player")

  // Main layout of the row (HBox)
  val mainLayout = children = Seq(
    imagePane,
    nameLabel,
    new Region {
      hgrow = Always
    },
    scoreLabel,
    activePlayer
  )
  this.getStyleClass.add("player-row")
}