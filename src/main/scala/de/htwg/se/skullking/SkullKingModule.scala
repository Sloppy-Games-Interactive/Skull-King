package de.htwg.se.skullking

import de.htwg.se.skullking.controller.ControllerComponent.{Controller, IController}
import de.htwg.se.skullking.model.CardComponent.{CardFactory, ICardFactory, IJokerCard, JokerCard}
import de.htwg.se.skullking.model.DeckComponent.{Deck, IDeck}
import de.htwg.se.skullking.model.HandComponent.{Hand, IHand}
import de.htwg.se.skullking.model.PlayerComponent.{IPlayerFactory, PlayerFactory}
import de.htwg.se.skullking.model.StateComponent.{GameState, IGameState}
import de.htwg.se.skullking.model.TrickComponent.{ITrick, Trick}

object SkullKingModule {
  given IGameState = GameState()
  given IController = Controller(summon[IGameState])
  given IHand = Hand()
  given IDeck = Deck()
  given IPlayerFactory = PlayerFactory
  given ITrick = Trick()
  given ICardFactory = CardFactory
  given IJokerCard = JokerCard()
}
