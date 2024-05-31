package de.htwg.se.skullking.model.player

import de.htwg.se.skullking.model.card.Card

/**
 * Player class for the GameState players list
 *
 * @param name of the player
 * @param hand cards witch hold the player in the hand
 * @param score personal score of the round?
 */
case class Player(
  name: String,
  hand: Hand = Hand(),
  score: Int = 0,
  prediction: Option[Int] = None,
  active: Boolean = false
) {
  /**
   *
   * @return new Player object with new hand cards (empty)
   */
  def resetHand: Player = this.copy(hand = Hand())
  def resetPrediction: Player = this.copy(prediction = None)
  def playCard(idx: Int): (Card, Player) = {
    val (card, newHand) = hand.play(idx)
    (card, this.copy(hand = newHand))
  }
  override def toString: String = s"$name: $score, $hand, $prediction"
}
