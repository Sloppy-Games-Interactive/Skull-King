import model.GameState

trait UI {
  def getPlayerNum(state: GameState): GameState
  def getPlayerNames(state: GameState): GameState
}

class Tui {

}
