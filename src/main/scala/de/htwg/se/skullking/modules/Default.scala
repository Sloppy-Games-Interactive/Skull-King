package de.htwg.se.skullking.modules

import de.htwg.se.skullking.controller.ControllerComponent.BaseControllerImpl.Controller
import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.{CardFactory, JokerCard}
import de.htwg.se.skullking.model.CardComponent.{ICardFactory, IJokerCard}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.{Deck, DeckFactory}
import de.htwg.se.skullking.model.DeckComponent.{IDeck, IDeckFactory}
import de.htwg.se.skullking.model.FileIOComponent.FileIOXmlImp.FileIO
import de.htwg.se.skullking.model.FileIOComponent.IFileIO
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, PlayerFactory}
import de.htwg.se.skullking.model.PlayerComponent.{IHand, IPlayerFactory}
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.IGameState
import de.htwg.se.skullking.model.TrickComponent.TrickBaseImpl.Trick
import de.htwg.se.skullking.model.TrickComponent.TrickBonusPointsHandlerAlternateImpl.TrickBonusPointsHandler as FunnyHahaTrickBonusPointsHandler
import de.htwg.se.skullking.model.TrickComponent.TrickBonusPointsHandlerBaseImpl.TrickBonusPointsHandler as BaseImplTrickBonusPointsHandler
import de.htwg.se.skullking.model.TrickComponent.TrickWinnerHandlerBaseImpl.TrickWinnerHandler
import de.htwg.se.skullking.model.TrickComponent.{ITrick, ITrickBonusPointsHandler, ITrickWinnerHandler}

object Default {
  given IGameState = GameState()
  given IController = Controller(summon[IGameState])
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
