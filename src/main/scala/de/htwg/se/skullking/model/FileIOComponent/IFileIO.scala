package de.htwg.se.skullking.model.FileIOComponent

import de.htwg.se.skullking.model.StateComponent.IGameState

trait IFileIO {
  def load: IGameState
  def save(gameState: IGameState): Unit
}
