package de.htwg.se.skullking.model.player

/**
 * Player class for the GameState players list
 *
 * @param name of the player
 * @param hand cards witch hold the player in the hand
 * @param score personal score of the round?
 */
class Player(val name: String = "", val hand: Hand = Hand(), val score: Int = 0, val prediction: Int = 0) {
  /**
   *
   * @return new Player object with new hand cards (empty)
   */
  def resetHand: Player = Player(name, Hand(), score)
  def resetPrediction: Player = Player(name, hand, score = 0)
}
