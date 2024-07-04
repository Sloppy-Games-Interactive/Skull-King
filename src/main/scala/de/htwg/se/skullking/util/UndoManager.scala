package de.htwg.se.skullking.util

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil
  def canUndo = undoStack.nonEmpty
  def canRedo = redoStack.nonEmpty
  def doStep(command: Command) = {
    undoStack = command :: undoStack
    command.doStep
    
    // Clear the redo stack because redo makes no sense after a new command has been executed
    redoStack = Nil
  }
  def undoStep = {
    undoStack match {
      case Nil =>
      case head :: stack => {
        head.undoStep
        undoStack = stack
        redoStack = head :: redoStack
      }
    }
  }
  def redoStep = {
    redoStack match {
      case Nil =>
      case head :: stack => {
        head.redoStep
        redoStack = stack
        undoStack = head :: undoStack
      }
    }
  }
}
