package de.htwg.se.skullking.view.gui.components

import scalafx.scene.layout.Pane
import de.htwg.se.skullking.model.CardComponent.*
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.transform.{Scale, Translate}
import scalafx.Includes.*

enum CardSize {
  case Mini, Small, Medium, XXMedium, Large
}

enum CardBack {
  case Back
}

class CardPane(card: ICard|CardBack, size: CardSize, hoverEffect: Boolean = true) extends Pane {
  def imagePathMap(card: ISpecialCard): String = card.suit match {
    case Suit.Pirate => s"/images/cards/special/PirateCard.png"
    case Suit.Mermaid => s"/images/cards/special/MermaidCard.png"
    case Suit.SkullKing => s"/images/cards/special/SkullKingCard.png"
    case Suit.Joker => s"/images/cards/special/JokerCard.png"
    case Suit.Escape => s"/images/cards/special/EscapeCard.png"
    case _ => "/images/cards/back.png"
  }

  def imagePathMap(card: IStandardCard): String = (card.suit, card.value) match {
    case (Suit.Red, value) if value >= 1 && value <= 14 => s"/images/cards/standard/parrot/ParrotCard$value.png"
    case (Suit.Yellow, value) if value >= 1 && value <= 14 => s"/images/cards/standard/treasure_chest/TreasureChestCard$value.png"
    case (Suit.Blue, value) if value >= 1 && value <= 14 => s"/images/cards/standard/octopus/OctopusCard$value.png"
    case (Suit.Black, value) if value >= 1 && value <= 14 => s"/images/cards/standard/jolly_roger/JollyRogerCard$value.png"
    case _ => "/images/cards/_CardBackside.png"
  }

  val imagePath = card match {
    case card: IStandardCard => imagePathMap(card)
    case card: ISpecialCard => imagePathMap(card)
    case _ => "/images/cards/_CardBackside.png"
  }

  val imgWidth = size match {
    case CardSize.Mini => 88
    case CardSize.Small => 176
    case CardSize.Medium => 221
    case CardSize.XXMedium => 265
    case CardSize.Large => 399
  }
  val imgHeight = size match {
    case CardSize.Mini => 130
    case CardSize.Small => 260
    case CardSize.Medium => 327
    case CardSize.XXMedium => 393
    case CardSize.Large => 590
  }

  children = Seq(
    new ImageView{
      image = new Image(imagePath)
      fitWidth = imgWidth
      fitHeight = imgHeight

      if (hoverEffect) {
        val scaleTransform = new Scale(1.0, 1.0)
        val translateTransform = new Translate(0, 0)

        transforms = Seq(scaleTransform, translateTransform)

        onMouseEntered = (event: MouseEvent) => {
          scaleTransform.x = 1.2
          scaleTransform.y = 1.2

          translateTransform.x = -(imgWidth * 0.1)
          translateTransform.y = -(imgHeight * 0.2)
        }

        onMouseExited = (event: MouseEvent) => {
          scaleTransform.x = 1.0
          scaleTransform.y = 1.0

          translateTransform.x = 0
          translateTransform.y = 0
        }
      }
    }
  )
}
