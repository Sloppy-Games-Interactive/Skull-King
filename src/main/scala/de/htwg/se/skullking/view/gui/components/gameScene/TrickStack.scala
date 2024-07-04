package de.htwg.se.skullking.view.gui.components.gameScene

import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.util.{ObservableEvent, Observer}
import de.htwg.se.skullking.view.gui.components.{CardEffect, CardPane, CardSize}
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.layout.{HBox, StackPane, VBox}
import scalafx.scene.transform.{Rotate, Translate}

import scala.util.Random

class TrickStack(controller: IController) extends HBox with Observer {
  controller.add(this)

  alignment = Pos.Center
  padding = Insets(100, 150, 0, 0)

  var trickStack: StackPane = new StackPane {
    alignment = Pos.Center
    children = Seq()
  }

  var cardTransforms: Map[ICard, (Rotate, Translate)] = Map()

  def update(event: ObservableEvent): Unit = {
    Platform.runLater {
      controller.state.activeTrick match {
        case Some(trick) =>
          trickStack.children = trick.cards.map(card => {
            val cardPane = new CardPane(card, CardSize.XXMedium, hoverEffect = CardEffect.None)

            // Check if the card's transforms are already stored in the map
            val (rotateTransform, translateTransform) = cardTransforms.get(card) match {
              case Some((rotate, translate)) =>
                // If the card's transforms are stored in the map, use them
                (rotate, translate)
              case None =>
                // If the card's transforms are not stored in the map, generate new ones
                val rotate = new Rotate(Random.nextInt(30) - 15) // Random rotation between -15 and 15 degrees
                val translate = new Translate(Random.nextInt(20) - 10, Random.nextInt(20) - 10) // Random offset between -10 and 10 in both x and y directions
                cardTransforms += card -> (rotate, translate) // Store the new transforms in the map
                (rotate, translate)
            }

            cardPane.transforms = Seq(rotateTransform, translateTransform)
            cardPane
          })
        case None =>
          // If the trick is completed, clear the map
          cardTransforms = Map()
          trickStack.children = Seq()
      }
    }
  }

  children = Seq(trickStack)
}
