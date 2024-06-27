package de.htwg.se.skullking.model.StateComponent

import de.htwg.se.skullking.model.CardComponent.ICard
import de.htwg.se.skullking.model.DeckComponent.{DeckContent, IDeck}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.trick.TrickComponent.ITrick

enum Phase {
  case PrepareGame
  case PrepareTricks
  case PlayTricks
  case EndGame
}

trait GameStateEvent()
case class SetPlayerLimitEvent(n: Int) extends GameStateEvent
case class AddPlayerEvent(player: IPlayer) extends GameStateEvent
case class SetPredictionEvent(player: IPlayer, prediction: Int) extends GameStateEvent
case class PlayCardEvent(player: IPlayer, card: ICard) extends GameStateEvent

trait IGameState {
  val phase: Phase
  val playerLimit: Int
  val players: List[IPlayer]
  val round: Int
  val tricks: List[ITrick]
  val deck: IDeck
  val roundLimit: Int
  
  def handleEvent(event: GameStateEvent): IGameState

  def activePlayer: Option[IPlayer]
  
  def activeTrick: Option[ITrick]
}