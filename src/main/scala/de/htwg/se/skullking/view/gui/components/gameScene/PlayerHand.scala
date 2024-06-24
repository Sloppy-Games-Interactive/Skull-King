package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.components.{CardPane, CardSize}
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.animation.TranslateTransition
import scalafx.util.Duration

class PlayerHand(
  controller: IController,
  onCardClicked: (card: ICard) => Unit
) extends VBox with Observer {
  controller.add(this)

  val translateYValue = 150
  alignment = Pos.BottomCenter
  translateY = translateYValue

  val transition = new TranslateTransition {
    duration = Duration(200) // Duration of the animation in milliseconds
    node = PlayerHand.this // The object to animate
    toY = 0 // The final value of translateY
  }

  onMouseEntered = _ => transition.playFromStart()

  transition.onFinished = _ => {
    transition.toY = if (translateY.value == 0) translateYValue else 0
  }

  onMouseExited = _ => transition.playFromStart()

  var handCards: HBox = new HBox {
    alignment = Pos.Center
    children = List()
  }

  def update(event: ObservableEvent): Unit = {
    Platform.runLater {
      val cards = controller.state.activePlayer match {
        case Some(player) => player.hand.cards
        case None => List()
      }

      val cardMargin = cards.size match {
        case size if size < 5 => 30
        case size if size >= 5 && size <= 7 => -50
        case size if size > 7 => -100
      }

      handCards.children = cards.map(card => new CardPane(card, CardSize.Medium) {
        padding = Insets(0, cardMargin, 0, 0)
        onMouseClicked = _ => onCardClicked(card)
      })
    }
  }

  children = handCards
}
