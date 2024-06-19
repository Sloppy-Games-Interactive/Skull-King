package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.card.Card
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.components.{BtnSize, CardPane, CardSize, GameButton}
import scalafx.geometry.Pos
import scalafx.scene.layout.{HBox, VBox}

class PlayCardPanel(
  controller: IController,
  onClose: () => Unit,
  onCardPlayed: (Player, Card) => Unit
) extends VBox {
  fillWidth = false
  alignment = Pos.Center
  styleClass.add("panel")

  var currentCard: Option[Card] = None
  var currentPlayer: Option[Player] = None

  var cardPreview: HBox = new HBox {
    alignment = Pos.Center
    children = Seq()
  }

  def openWithCard(card: Card, player: Player): Unit = {
    currentCard = Some(card)
    currentPlayer = Some(player)

    cardPreview.children = (currentCard, currentPlayer) match {
      case (Some(card), Some(player)) => Seq(new CardPane(card, CardSize.Large, hoverEffect = false))
      case _ => Seq()
    }
  }

  children = Seq(
    cardPreview,
    new HBox {
      children = Seq(
        new GameButton(BtnSize.medium) {
          text = "Play Card"
          onAction = _ => {
            (currentCard, currentPlayer) match {
              case (Some(card), Some(player)) =>
                onCardPlayed(player, card)
              case _ =>
            }
            onClose()
          }
        },
        new GameButton(BtnSize.medium) {
          text = "Cancel"
          onAction = _ => {
            onClose()
          }
        }
      )
    }
  )
}
