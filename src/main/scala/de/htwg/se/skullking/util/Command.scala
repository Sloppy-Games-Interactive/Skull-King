package de.htwg.se.skullking.util

trait Command {
  def doStep: Unit
  def undoStep: Unit
  def redoStep: Unit
}
