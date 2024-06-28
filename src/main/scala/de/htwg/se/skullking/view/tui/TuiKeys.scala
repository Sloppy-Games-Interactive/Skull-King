package de.htwg.se.skullking.view.tui

enum TuiKeys(val key: String) {
  case NewGame extends TuiKeys(":new game")
  case Quit extends TuiKeys(":quit")
  case Undo extends TuiKeys(":undo")
  case Redo extends TuiKeys(":redo")
  case Save extends TuiKeys(":save")
  case Load extends TuiKeys(":load")
}