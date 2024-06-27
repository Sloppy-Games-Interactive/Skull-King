package de.htwg.se.skullking.modules

import de.htwg.se.skullking.controller.ControllerComponent.BaseControllerImpl.Controller
import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.{CardFactory, JokerCard}
import de.htwg.se.skullking.model.CardComponent.{ICardFactory, IJokerCard}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.{Deck, DeckFactory}
import de.htwg.se.skullking.model.DeckComponent.{IDeck, IDeckFactory}
import de.htwg.se.skullking.model.FileIOComponent.FileIOXmlImp.FileIO
import de.htwg.se.skullking.model.FileIOComponent.{FileIOJsonImpl, IFileIO}
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, PlayerFactory}
import de.htwg.se.skullking.model.PlayerComponent.{IHand, IPlayerFactory}
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.IGameState
import de.htwg.se.skullking.model.trick.TrickBonusPointsHandlerComponent.ITrickBonusPointsHandler
import de.htwg.se.skullking.model.trick.TrickBonusPointsHandlerComponent.TrickBonusPointsHandlerAlternateImpl.TrickBonusPointsHandler as FunnyHahaTrickBonusPointsHandler
import de.htwg.se.skullking.model.trick.TrickBonusPointsHandlerComponent.TrickBonusPointsHandlerBaseImpl.TrickBonusPointsHandler as BaseImplTrickBonusPointsHandler
import de.htwg.se.skullking.model.trick.TrickComponent.ITrick
import de.htwg.se.skullking.model.trick.TrickComponent.TrickBaseImpl.Trick
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

  given baseImplBonusPointsHandler: ITrickBonusPointsHandler = BaseImplTrickBonusPointsHandler()
//  given funnyHahaBonusPointsHandler: ITrickBonusPointsHandler = FunnyHahaTrickBonusPointsHandler()
  given ITrickWinnerHandler = TrickWinnerHandler()

  given IFileIO = FileIO()
}
