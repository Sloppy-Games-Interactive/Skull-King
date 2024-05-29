import de.htwg.se.skullking.model.GameState
import de.htwg.se.skullking.model.player.Player
//import de.htwg.se.skullking.model._
//
//val state = GameState()

// that's one way to sort cards when printing a player hand...
//state.startNewRound.deck.shuffle().cards.sortBy(_.value).groupBy(_.suit).foreach(println)


class UndoManager(controller: Controller) {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil
  def doStep(command: Command) = {
    undoStack = command :: undoStack
    controller.state = command.doStep
  }
  def undoStep = {
    undoStack match {
      case Nil =>
      case head :: stack => {
        controller.state = head.undoStep
        undoStack = stack
        redoStack = head :: redoStack
      }
    }
  }
  def redoStep = {
    redoStack match {
      case Nil =>
      case head :: stack => {
        controller.state = head.redoStep
        redoStack = stack
        undoStack = head :: undoStack
      }
    }
  }
}

trait Command {
  def doStep: GameState
  def undoStep: GameState
  def redoStep: GameState
}

class NewGameCommand(val state: GameState) extends Command {
  override def doStep: GameState = GameState()
  override def undoStep: GameState = state
  override def redoStep: GameState = doStep
}

class AddPlayerCommand(val state: GameState, player: Player) extends Command {
  override def doStep: GameState = state.addPlayer(player)
  override def undoStep: GameState = state
  override def redoStep: GameState = doStep
}

class PrepareRoundCommand(val state: GameState) extends Command {
  override def doStep: GameState = state.startNewRound
  override def undoStep: GameState = state
  override def redoStep: GameState = doStep
}

class DealCardsCommand(val state: GameState) extends Command {
  override def doStep: GameState = state.dealCards
  override def undoStep: GameState = state
  override def redoStep: GameState = doStep
}

class SetPredictionCommand(val state: GameState, player: Player, prediction: Int) extends Command {
  override def doStep: GameState = state.setPrediction(player, prediction)
  override def undoStep: GameState = state
  override def redoStep: GameState = doStep
}

class Controller {
  val undoManager = new UndoManager(this)
  var state = GameState()
  def newGame = undoManager.doStep(new NewGameCommand(state))
  def addPlayer(name: String) = undoManager.doStep(new AddPlayerCommand(state, Player(name)))
  def prepareRound = undoManager.doStep(new PrepareRoundCommand(state))
  def dealCards = undoManager.doStep(new DealCardsCommand(state))
  def setPrediction(player: Player, prediction: Int) = undoManager.doStep(new SetPredictionCommand(state, player, prediction))
  def undo = undoManager.undoStep
  def redo = undoManager.redoStep
}
//   def newGame: Unit = {
//    state = GameState()
//    notifyObservers(ControllerEvents.NewGame)
//  }
//
//  def addPlayer(name: String): Unit = {
//    state = state.addPlayer(Player(name))
//    notifyObservers(ControllerEvents.PlayerAdded)
//  }
//
//  def prepareRound: Unit = {
//    state = state.startNewRound
//    notifyObservers(ControllerEvents.RoundPrepared)
//  }
//
//  def dealCards: Unit = {
//    state = state.dealCards
//    notifyObservers(ControllerEvents.CardsDealt)
//  }
//
//  def startTrick: Unit = {
//    //TODO: implement
//    notifyObservers(ControllerEvents.TrickStarted)
//
//  }
//
//  def setPrediction(player: Player, prediction: Int): Unit = {
//    state = state.setPrediction(player, prediction)
//    notifyObservers(ControllerEvents.PredictionSet)
//  }
