package de.htwg.se.skullking.view.gui.components

import scalafx.scene.control.Label
import scalafx.scene.layout.HBox

class PlayerListRow (playerName: String, playerScore: Int) extends HBox{
  val name: String = playerName
  val score: Int = playerScore

  children = Seq(
    new Label {
      text = name
    },
    new Label {
      text = score.toString
    }
  )

}
