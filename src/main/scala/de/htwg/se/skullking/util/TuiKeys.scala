package de.htwg.se.skullking.util

enum TuiKeys(val key: String) {
  case NewGame extends TuiKeys("n")
  case Quit extends TuiKeys("q")
  case Undo extends TuiKeys("z")
  case Redo extends TuiKeys("y")
  case AddPlayer extends TuiKeys("p")
  case PlayCard extends TuiKeys("c")
  case PrepareRound extends TuiKeys("r")
  case DealCards extends TuiKeys("d")
  case StartTrick extends TuiKeys("yo ho ho")
  case SetPrediction extends TuiKeys("pt")
}