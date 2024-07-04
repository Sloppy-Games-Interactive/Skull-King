package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.Styles
import de.htwg.se.skullking.view.gui.components.BtnSize.medium
import de.htwg.se.skullking.view.gui.components.GameButton
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.layout.VBox

import scala.collection.immutable.List

class ScoreboardPanel(
  controller: IController,
  onClickQuitBtn: () => Unit,
  height: Int = 3000,
  width: Int = 1191
) extends VBox with Observer {
  controller.add(this)

  prefHeight = height
  prefWidth = width
  fillWidth = false

  alignment = Pos.Center
  styleClass.add("panel")
  
  val btn = new GameButton(medium) {
    onAction = onClickQuitBtn
    text = "Close Scoreboard"
  }


  def update(event: ObservableEvent): Unit = {
// TODO: Fix crash reason

//    children = Seq(
//      btn
//    )

  }

  this.getStylesheets.add(Styles.panelCss)

}
