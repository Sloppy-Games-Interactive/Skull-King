package de.htwg.se.skullking.view.gui.components

import de.htwg.se.skullking.model.CardComponent.*
import scalafx.Includes.*
import scalafx.animation.{RotateTransition, ScaleTransition, Timeline, TranslateTransition}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Pane, StackPane}
import scalafx.scene.transform.{Rotate, Scale, Translate}
import scalafx.util.Duration

import scala.language.postfixOps

enum CardSize {
  case Mini, Small, Medium, XXMedium, Large
}

enum CardBack {
  case Back
}

enum CardEffect {
  case None, Flip, FlipOnce, Enlarge
}

class CardPane(
  card: ICard|CardBack,
  size: CardSize,
  hoverEffect: CardEffect = CardEffect.Enlarge,
  showFaceUp: Boolean = true
) extends Pane {
  private val backImg = "/images/cards/_CardBackside.png"
  private val imgWidth = size match {
    case CardSize.Mini => 88
    case CardSize.Small => 176
    case CardSize.Medium => 221
    case CardSize.XXMedium => 265
    case CardSize.Large => 399
  }
  private val imgHeight = size match {
    case CardSize.Mini => 130
    case CardSize.Small => 260
    case CardSize.Medium => 327
    case CardSize.XXMedium => 393
    case CardSize.Large => 590
  }

  private var isAnimating = false
  private var isHovering = false
  private var shouldFlipAfterAnimation = false

  private val front = createImageView(imagePath)
  private val back = createImageView(backImg)
  private val stackPane = createStackPane(Seq(front, back))

  if (showFaceUp) {
    front.visible = true
    back.visible = false
    back.scaleX = -1
  } else {
    front.scaleX = -1
  }

  private val rotateTransition = createRotateTransition(stackPane)
  private val timeline = createTimeline()

  children = Seq(stackPane)

  private def createImageView(imagePath: String): ImageView = new ImageView {
    image = new Image(imagePath)
    fitWidth = imgWidth
    fitHeight = imgHeight
  }

  private def createStackPane(ch: Seq[ImageView]): StackPane = new StackPane {
    children = ch
    hoverEffect match {
      case CardEffect.Enlarge => {
        val scaleTransform = new Scale(1.0, 1.0)
        val translateTransform = new Translate(0, 0)
        transforms = Seq(scaleTransform, translateTransform)

        val scaleTransition = new ScaleTransition {
          duration = Duration(200)
          node = CardPane.this // Das zu animierende Objekt
          fromX = 1.0
          fromY = 1.0
          toX = 1.2
          toY = 1.2
        }

        val translateTransition = new TranslateTransition {
          duration = Duration(200)
          node = CardPane.this // Das zu animierende Objekt
          fromX = 0
          fromY = 0
          toX = -(imgWidth * 0.1)
          toY = -(imgHeight * 0.2)
        }

        onMouseEntered = (event: MouseEvent) => {
          scaleTransition.playFromStart()
          translateTransition.playFromStart()
        }

        onMouseExited = (event: MouseEvent) => {
          scaleTransition.rate = -1 // Rückwärts abspielen
          translateTransition.rate = -1 // Rückwärts abspielen
          scaleTransition.play()
          translateTransition.play()
        }
      }
      case CardEffect.FlipOnce => {
        onMouseEntered = _ => {
          if (showFaceUp && front.visible.value || !showFaceUp && back.visible.value) {
            flip()
          }
        }
      }
      case CardEffect.Flip => {
        onMouseEntered = _ => {
          isHovering = true
          if (!isAnimating) flip()
        }
        onMouseExited = _ => {
          isHovering = false
          if (!isAnimating) {
            flip()
          } else {
            shouldFlipAfterAnimation = true
          }
        }
      }
      case _ => ()
    }
  }

  private def createRotateTransition(n: StackPane): RotateTransition = new RotateTransition {
    axis = Rotate.YAxis
    duration = Duration(500)
    node = n
    byAngle = 180
    onFinished = _ => {
      isAnimating = false
      if (shouldFlipAfterAnimation && !isHovering) {
        flip()
      }
      shouldFlipAfterAnimation = false
    }
  }

  private def createTimeline(): Timeline = new Timeline {
    onFinished = _ => {
      if (back.visible.value) {
        front.visible = true
        back.visible = false
      } else {
        front.visible = false
        back.visible = true
      }
    }
    keyFrames = Seq(at (Duration(rotateTransition.duration.value.toMillis / 2)) { Set() })
  }

  def flipFaceUp(): Unit = {
    if (!front.visible.value || back.visible.value) {
      flip()
    }
  }

  def flip(): Unit = {
    isAnimating = true
    timeline.playFromStart()
    rotateTransition.playFromStart()
  }

  private def imagePath: String = card match {
    case card: IStandardCard => standardCardImagePath(card)
    case card: ISpecialCard => specialCardImagePath(card)
    case _ => backImg
  }

  private def standardCardImagePath(card: IStandardCard): String = (card.suit, card.value) match {
    case (Suit.Red, value) if value >= 1 && value <= 14 => s"/images/cards/standard/parrot/ParrotCard$value.png"
    case (Suit.Yellow, value) if value >= 1 && value <= 14 => s"/images/cards/standard/treasure_chest/TreasureChestCard$value.png"
    case (Suit.Blue, value) if value >= 1 && value <= 14 => s"/images/cards/standard/octopus/OctopusCard$value.png"
    case (Suit.Black, value) if value >= 1 && value <= 14 => s"/images/cards/standard/jolly_roger/JollyRogerCard$value.png"
    case _ => backImg
  }

  private def specialCardImagePath(card: ISpecialCard): String = card.suit match {
    case Suit.Pirate => s"/images/cards/special/PirateCard.png"
    case Suit.Mermaid => s"/images/cards/special/MermaidCard.png"
    case Suit.SkullKing => s"/images/cards/special/SkullKingCard.png"
    case Suit.Joker => s"/images/cards/special/JokerCard.png"
    case Suit.Escape => s"/images/cards/special/EscapeCard.png"
    case _ => backImg
  }
}