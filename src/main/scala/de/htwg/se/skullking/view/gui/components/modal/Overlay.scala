package de.htwg.se.skullking.view.gui.components.modal

import scalafx.scene.control.Button
import scalafx.scene.effect.{BoxBlur, GaussianBlur}
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.layout.{Pane, StackPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node

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
  
  def openModal(): Unit = {
    updateSnapshot()
    modal.visible = true
  }
  
  def closeModal(): Unit = {
    modal.visible = false
    imageView.image = null
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