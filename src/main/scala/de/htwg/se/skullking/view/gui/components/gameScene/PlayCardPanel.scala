package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.components.{BtnSize, CardEffect, CardPane, CardSize, GameButton}
import scalafx.geometry.Pos
import scalafx.scene.layout.{HBox, VBox}

class PlayCardPanel(
  controller: IController,
  onClose: () => Unit,
  onCardPlayed: (IPlayer, ICard) => Unit
) extends VBox {
  fillWidth = false
  alignment = Pos.Center
  styleClass.add("panel")

  var currentCard: Option[ICard] = None
  var currentPlayer: Option[IPlayer] = None

  var cardPreview: HBox = new HBox {
    alignment = Pos.Center
    children = Seq()
  }

  def openWithCard(card: ICard, player: IPlayer): Unit = {
    currentCard = Some(card)
    currentPlayer = Some(player)

    cardPreview.children = (currentCard, currentPlayer) match {
      case (Some(card), Some(player)) => Seq(new CardPane(card, CardSize.Large, hoverEffect = CardEffect.None))
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
