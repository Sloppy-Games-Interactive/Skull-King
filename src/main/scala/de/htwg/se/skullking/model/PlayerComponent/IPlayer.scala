package de.htwg.se.skullking.model.PlayerComponent

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.HandComponent.IHand

trait IPlayer {
  val name: String
  val hand: IHand
  val score: Int
  val prediction: Option[Int]
  val active: Boolean

  def resetHand: IPlayer
  
  def resetPrediction: IPlayer
  
  def playCard(card: ICard): (ICard, IPlayer)
  
  def setHand(hand: IHand): IPlayer
  
  def setPrediction(prediction: Int): IPlayer
  
  def setScore(score: Int): IPlayer
  
  def setActive(active: Boolean): IPlayer
}

trait IPlayerFactory {
  def create(name: String): IPlayer
}
