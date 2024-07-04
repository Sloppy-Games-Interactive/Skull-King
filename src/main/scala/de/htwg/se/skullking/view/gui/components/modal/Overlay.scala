package de.htwg.se.skullking.view.gui.components.modal

import scalafx.Includes.*
import scalafx.animation.FadeTransition
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.effect.BoxBlur
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.util.Duration

class Overlay(
   windowWidth: Double,
   windowHeight: Double,
   sceneContent: () => Node,
   modalBox: Node,
   var onOpenFinish: () => Unit = () => (),
   var onCloseFinish: () => Unit = () => ()
) {
  val imageView = new ImageView{
    fitWidth <== windowWidth
    fitHeight <== windowHeight
    preserveRatio = false
  }

  val overlay = new Rectangle {
    width = windowWidth
    height = windowHeight
    fill = Color.rgb(0, 0, 0, 0.35) // semi-transparent black
  }

  def updateSnapshot(): Unit = {
    // Take a snapshot of the content
    val snapshot = new WritableImage(windowWidth.toInt, windowHeight.toInt)
    sceneContent().snapshot(null, snapshot)

    imageView.image = snapshot

    // Update the ImageView with the blurred snapshot
    imageView.effect = new BoxBlur{
      width = 5
      height = 5
      iterations = 3
    }
  }
  
  def openModal(fadeIn: Boolean = false): Unit = {
    updateSnapshot()
    modal.visible = true

    if (fadeIn) {
      val fadeInTransition = new FadeTransition {
        node = modal
        fromValue = 0
        toValue = 1
        duration = Duration(500)
        onFinished = _ => onOpenFinish()
      }
      fadeInTransition.play()
    }
  }
  
  def closeModal(fadeOut: Boolean = false): Unit = {
    if (fadeOut) {
      val fadeOutTransition = new FadeTransition {
        node = modal
        fromValue = 1
        toValue = 0
        duration = Duration(500)
        onFinished = _ => {
          modal.visible = false
          imageView.image = null
          onCloseFinish()
        }
      }
      fadeOutTransition.play()
    } else {
      modal.visible = false
      imageView.image = null
      onCloseFinish()
    }
  }

  def toggleModal(): Unit = {
    if (modal.visible.value) {
      closeModal()
    } else {
      openModal()
    }
  }

  val modal = new StackPane {
    children = Seq(
      overlay,
      modalBox
    )
    visible = false
    alignment = Pos.Center
    prefWidth = windowWidth
    prefHeight = windowHeight
  }
}