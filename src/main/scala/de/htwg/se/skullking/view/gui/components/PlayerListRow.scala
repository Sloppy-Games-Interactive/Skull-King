package de.htwg.se.skullking.view.gui.components

import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.trick.TrickComponent.ITrick
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.effect.InnerShadow
import scalafx.scene.image.ImageView
import scalafx.scene.layout.Priority.Always
import scalafx.scene.layout.{HBox, Pane, Region}
import scalafx.scene.paint.Color

class PlayerListRow (player: IPlayer, tricks: List[ITrick]) extends HBox{
  private val truncatedName = if (player.name.length > 11) {
    player.name.take(8) + "..."
  } else player.name

  private val name = truncatedName
  private val score = player.score
  private val isCurrentPlayer = player.active
  private val prediction = player.prediction.getOrElse(0)

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

  val wonTricks = for {
    trick <- tricks
    winner <- trick.winner if winner.name == player.name
  } yield trick
  val numTricksWon = wonTricks.length

  val predictionText = s"$numTricksWon / $prediction"
  private val predictionLabel: Label = new Label(predictionText)
  predictionLabel.getStyleClass.add("prediction-label")


//  val activePlayerMark = "☠"
  val activePlayerMark = "#"
  private val activePlayer: Label = new Label(activePlayerMark) {
    visible = isCurrentPlayer
    effect = new InnerShadow {
      color = Color.White
      radius = 10
      choke = 0.5
    }
  }
  activePlayer.getStyleClass.add("active-player")

  val playerRowBox = new HBox {
    children = Seq(
      imagePane,
      nameLabel,
      new Region {
        hgrow = Always
      },
      scoreLabel,
      activePlayer
    )
  }
  playerRowBox.getStyleClass.add("player-row")

  val predictionDisplay = new HBox {
    children = Seq(
      predictionLabel
    )
  }
  predictionDisplay.getStyleClass.add("prediction-display")

  // Main layout of the row (HBox)
  val mainLayout = children = Seq(
    playerRowBox,
    predictionDisplay
  )
}