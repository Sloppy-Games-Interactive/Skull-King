package de.htwg.se.skullking.util

enum TuiKeys(val key: String) {
  case NewGame extends TuiKeys(":new game")
  case Quit extends TuiKeys(":quit")
  case Undo extends TuiKeys(":undo")
  case Redo extends TuiKeys(":redo")
}