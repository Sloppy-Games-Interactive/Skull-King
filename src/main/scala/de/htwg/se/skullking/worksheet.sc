import de.htwg.se.skullking.model.deck.{Deck, DeckContent, DeckFactory}
import de.htwg.se.skullking.model.player.Player
import de.htwg.se.skullking.model.trick.Trick

enum Phase {
  case PrepareGame
  case PrepareTricks
  case PlayTricks
  case EndGame
}

case class State(
  phase: Phase = Phase.PrepareGame,
  playerLimit: Int = 0,
  players: List[Player] = List(),
  round: Int = 0,
  tricks: List[Trick] = List(),
  activePlayer: Option[Player] = None,
  deck: Deck = Deck(),
) {
  private def changePhase(nextPhase: Phase): State = this.copy(phase = nextPhase)

  def setPlayerLimit(n: Int): State = this.copy(playerLimit = n)

  def addPlayer(player: Player): State = {
    if (players.length < playerLimit) {
      this.copy(players = player :: players)
    } else {
      prepareRound
    }
  }

  private def prepareRound: State = {
    this.copy(
        round = round + 1,
        deck = DeckFactory(DeckContent.full).shuffle()
      ).dealCards
      .changePhase(Phase.PrepareTricks)
  }

  private def dealCards: State = {
    val (newDeck, updatedPlayers) = players.foldLeft((deck, List[Player]())) {
      case ((currentDeck, playerList), player) =>
        val (newDeck, newHand) = player.hand.drawFromDeck(currentDeck, round)
        (newDeck, player.copy(hand = newHand) :: playerList)
    }

    this.copy(players = updatedPlayers.reverse, deck = newDeck)
  }

  def setPrediction(player: Player, newPrediction: Int): State = {
    val updatedPlayers = players.collect {
      case p if p.name == player.name => p.copy(prediction = Some(newPrediction))
      case p => p
    }

    val nextState = this.copy(players = updatedPlayers)

    if (updatedPlayers.forall(_.prediction.isDefined)) {
      nextState.startTrick
    } else {
      nextState
    }
  }

  private def startTrick: State = {
    this.copy(
      tricks = Trick() :: tricks,
      activePlayer = Some(players.head)
    ).changePhase(Phase.PlayTricks)
  }

  private def findNextPlayer: Option[Player] = {
    activePlayer match {
      case Some(player) =>
        val nextIndex = players.indexOf(player) + 1
        players.lift(nextIndex)
      case None => None
    }
  }

  def playCard: State = { // todo: implement actually playing cards
    val nextPlayer = findNextPlayer
    if (nextPlayer.isEmpty) {
      endTrick
    } else {
      this.copy(activePlayer = nextPlayer)
    }
  }

  private def endTrick: State = {
    if (tricks.length == round) {
      endRound
    } else {
      startTrick
    }
  }

  private def endRound: State = {
    val updatedPlayers = players.map { player =>
      val wonTricks = tricks.collect {
        case trick if trick.winner.contains(player) => trick
      }

      val numTricksWon = wonTricks.length

      player.prediction.fold(player) { prediction =>
        val scoreChange = prediction match {
          case 0 if numTricksWon == 0 => 10 * round
          case 0 => -10 * round
          case _ if prediction == numTricksWon => 20 * numTricksWon + wonTricks.map(_.calculateBonusPoints).sum
          case _ => -10 * Math.abs(numTricksWon - prediction)
        }
        player.copy(score = player.score + scoreChange)
      }
    }

    val nextState = this.copy(
      players = updatedPlayers,
      tricks = List()
    )

    if (round == 10) {
      nextState.changePhase(Phase.EndGame)
    } else {
      nextState.prepareRound
    }
  }
}
