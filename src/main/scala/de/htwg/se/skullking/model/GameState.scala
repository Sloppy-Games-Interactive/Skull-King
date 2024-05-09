package de.htwg.se.skullking.model

/**
 * Global GameState manager
 *
 * @param players list with all players
 * @param round current round
 * @param deck new full deck
 */
class GameState(val players: List[Player] = List(), val round: Int = 0, val deck: Deck = Deck()) {

  /**
   * add player to global GameState player list
   * @param player player object to add
   * @return new GameState
   */
  def addPlayer(player: Player): GameState = GameState(player :: players, round, deck)

  /**
   * set prediction for player
   * @param player player object to set prediction
   * @param prediction prediction value
   * @return new GameState
   */
  def setPrediction(player: Player, prediction: Int): GameState = {
    
    GameState(
      players.map((p: Player) => {
        if (p.name == player.name) {
          Player(p.name, p.hand, p.score, prediction)
        } else {
          p
        }
      }),
      round,
      deck
    )
  }

  /**
   * start new game round with new deck
   * @return new GameState
   */
  def startNewRound: GameState = {
    val redCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Red, value))
    val blueCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Blue, value))
    val yellowCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Yellow, value))
    val blackCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Black, value))
    val specialCards: Vector[Card] = Vector(Card(SpecialSuit.Joker, 0), Card(SpecialSuit.Mermaid, 0),
      Card(SpecialSuit.SkullKing, 0), Card(SpecialSuit.Pirate, 0), Card(SpecialSuit.Escape, 0))

    // add all cards to one list
    val allCards: List[Card] = (redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList

    val fullDeck: Deck = Deck(allCards)

    // next round?
    GameState(
      players.map((player: Player) => player.resetHand),
      round + 1,
      fullDeck.shuffle()
    )
  }

  /**
   * Deal cards to all players
   * @return new gameState
   */
  def dealCards: GameState = {
    var currentDeck = deck
    GameState(
      players.map((player: Player) => {
        val (newDeck, newHand) = player.hand.drawFromDeck(currentDeck, round)
        currentDeck = newDeck
        Player(player.name, newHand, player.score)
      }),
      round,
      currentDeck
    )
  }

  def getStatusAsTable: String = {
      s"""
        |Round: $round
        |Players:
        |${players.sortBy(_.score).map((player: Player) => {
        (player.name + " Prediction: " + player.prediction + ":\n" + player.hand.toString).patch(0, "  ", 0)}).mkString("\n")
      }
        |""".stripMargin
  }
}
