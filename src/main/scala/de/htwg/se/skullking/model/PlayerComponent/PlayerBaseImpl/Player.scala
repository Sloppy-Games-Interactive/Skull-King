package de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.PlayerComponent._

case class Player(
  name: String,
  hand: IHand = Hand(),
  score: Int = 0,
  prediction: Option[Int] = None,
  active: Boolean = false
) extends IPlayer {
  def resetHand: IPlayer = this.copy(hand = Hand())
  
  def resetPrediction: IPlayer = this.copy(prediction = None)
  
  def playCard(card: ICard): (ICard, IPlayer) = {
    hand.indexOf(card) match {
      case Some(idx) => {
        val (card, newHand) = hand.play(idx)
        (card, this.copy(hand = newHand))
      }
      case None => (card, this)
    }
  }
  
  def setHand(hand: IHand): IPlayer = this.copy(hand = hand)
  
  def setPrediction(prediction: Int): IPlayer = this.copy(prediction = Some(prediction))
  
  def setScore(score: Int): IPlayer = this.copy(score = score)
  
  def setActive(active: Boolean): IPlayer = this.copy(active = active)
  
  override def toString: String = s"$name: $score, $hand, prediction: ${prediction.getOrElse("-")}"
}

object PlayerFactory extends IPlayerFactory {
  def create(name: String): IPlayer = Player(name)
} 
