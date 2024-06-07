package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.Controller
import de.htwg.se.skullking.model.card.Card
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.components.{BtnSize, CardPane, CardSize, GameButton}
import scalafx.geometry.Pos
import scalafx.scene.layout.{HBox, VBox}

class PlayCardPanel(
  controller: Controller,
  onClose: () => Unit,
  onCardPlayed: (Player, Card) => Unit
) extends VBox {
  fillWidth = false
  alignment = Pos.Center
  styleClass.add("panel")

  var currentCard: Option[Card] = None
  var currentPlayer: Option[Player] = None

  def openWithCard(card: Card, player: Player): Unit = {
    currentCard = Some(card)
    currentPlayer = Some(player)
  }

  children = Seq(
    new HBox {
      alignment = Pos.Center
      children = (currentCard, currentPlayer) match {
        case (Some(card), Some(player)) => Seq(new CardPane(card, CardSize.Large))
        case _ => Seq()
      }
    },
    new HBox {
      children = Seq(
        new GameButton(BtnSize.large) {
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
        new GameButton(BtnSize.large) {
          text = "Cancel"
          onAction = _ => {
            onClose()
          }
        }
      )
    }
  )
}
