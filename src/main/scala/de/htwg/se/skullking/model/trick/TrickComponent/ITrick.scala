package de.htwg.se.skullking.model.trick.TrickComponent

import de.htwg.se.skullking.model.CardComponent.{ICard, Suit}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer

trait ITrick {
  val stack: List[(ICard, IPlayer)]

  def cards: List[ICard]

  def players: List[IPlayer]

  def leadSuit: Suit | Any

  def hasSkullKing: Boolean

  def hasPirate: Boolean

  def hasMermaid: Boolean

  def play(card: ICard, player: IPlayer): ITrick

  def winner: Option[IPlayer]

  def calculateBonusPoints: Int
}




