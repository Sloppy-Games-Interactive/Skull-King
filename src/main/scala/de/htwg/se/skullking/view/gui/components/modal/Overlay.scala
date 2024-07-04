package de.htwg.se.skullking.view.gui.components.modal

import scalafx.scene.control.Button
import scalafx.scene.effect.{BoxBlur, GaussianBlur}
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.layout.{Pane, StackPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.Includes.*
import scalafx.animation.FadeTransition
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node
import scalafx.util.Duration

class Overlay(windowWidth: Double, windowHeight: Double, sceneContent: () => Node, modalBox: Node) {
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
        duration = Duration(500) // Duration of the fadeIn effect in milliseconds
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
        duration = Duration(500) // Duration of the fadeOut effect in milliseconds
      }
      fadeOutTransition.onFinished = _ => {
        modal.visible = false
        imageView.image = null
      }
      fadeOutTransition.play()
    } else {
      modal.visible = false
      imageView.image = null
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