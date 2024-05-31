package de.htwg.se.skullking.model

import de.htwg.se.skullking.model.deck.{Deck, DeckContent, DeckFactory}
import de.htwg.se.skullking.model.player.Player

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
    val fullDeck: Deck = DeckFactory(DeckContent.full)

    // next round?
    GameState(
      players.map((player: Player) => player.resetHand.resetPrediction),
      round + 1,
      fullDeck.shuffle()
    )
  }
  
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

  def playCard(player: Player, cardIndex: Int): GameState = {
    val adjustedCardIndex = cardIndex - 1
    if (player.hand.cards.nonEmpty && adjustedCardIndex < player.hand.cards.length) {
      player.hand.play(adjustedCardIndex) match {
        case (card, newHand) => {
          GameState(
            players.map((p: Player) => {
              if (p.name == player.name) {
                Player(p.name, newHand, p.score)
              } else {
                p
              }
            }),
            round,
            deck
          )
        }
      }
    } else {
      println(s"${player.name}'s hand is empty or the card index is out of range.")
      this
    }
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
