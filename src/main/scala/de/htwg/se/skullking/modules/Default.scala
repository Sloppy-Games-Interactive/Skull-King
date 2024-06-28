package de.htwg.se.skullking.modules

import de.htwg.se.skullking.controller.ControllerComponent.BaseControllerImpl.Controller
import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.{CardFactory, JokerCard}
import de.htwg.se.skullking.model.CardComponent.{ICardFactory, IJokerCard}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.{Deck, DeckFactory}
import de.htwg.se.skullking.model.DeckComponent.{IDeck, IDeckFactory}
import de.htwg.se.skullking.model.FileIOComponent.FileIOXmlImp.FileIO
import de.htwg.se.skullking.model.FileIOComponent.{FileIOJsonImpl, IFileIO}
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, HandFactory, PlayerFactory}
import de.htwg.se.skullking.model.PlayerComponent.{IHand, IHandFactory, IPlayerFactory}
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.{GameState, GameStateFactory}
import de.htwg.se.skullking.model.StateComponent.{IGameState, IGameStateFactory}
import de.htwg.se.skullking.model.trick.TrickBonusPointsHandlerComponent.ITrickBonusPointsHandler
import de.htwg.se.skullking.model.trick.TrickBonusPointsHandlerComponent.TrickBonusPointsHandlerAlternateImpl.TrickBonusPointsHandler as FunnyHahaTrickBonusPointsHandler
import de.htwg.se.skullking.model.trick.TrickBonusPointsHandlerComponent.TrickBonusPointsHandlerBaseImpl.TrickBonusPointsHandler as BaseImplTrickBonusPointsHandler
import de.htwg.se.skullking.model.trick.TrickComponent.{ITrick, ITrickFactory}
import de.htwg.se.skullking.model.trick.TrickComponent.TrickBaseImpl.{Trick, TrickFactory}
import de.htwg.se.skullking.model.trick.TrickWinnerHandlerComponent.ITrickWinnerHandler
import de.htwg.se.skullking.model.trick.TrickWinnerHandlerComponent.TrickWinnerHandlerBaseImpl.TrickWinnerHandler

object Default {
  given IGameState = GameState()
  given IController = Controller(using summon[IGameState])
  given IHand = Hand()
  given IDeck = Deck()
  given IPlayerFactory = PlayerFactory
  given ITrick = Trick()
  given ICardFactory = CardFactory
  given IJokerCard = JokerCard()
  given IDeckFactory = DeckFactory
  given IHandFactory = HandFactory
  given ITrickFactory = TrickFactory
  given IGameStateFactory = GameStateFactory

  given baseImplBonusPointsHandler: ITrickBonusPointsHandler = BaseImplTrickBonusPointsHandler()
//  given funnyHahaBonusPointsHandler: ITrickBonusPointsHandler = FunnyHahaTrickBonusPointsHandler()
  given ITrickWinnerHandler = TrickWinnerHandler()

  //given IFileIO = FileIOJsonImpl.FileIO()
  given IFileIO = FileIO()
}
