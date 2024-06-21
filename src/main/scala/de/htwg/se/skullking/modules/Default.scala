package de.htwg.se.skullking.modules

import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Guice, Injector, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.skullking.controller.ControllerComponent.BaseControllerImpl.Controller
import de.htwg.se.skullking.controller.ControllerComponent.IController
import de.htwg.se.skullking.model.CardComponent.CardBaseImpl.{CardFactory, JokerCard}
import de.htwg.se.skullking.model.CardComponent.{ICard, ICardFactory, IJokerCard, JokerBehaviour}
import de.htwg.se.skullking.model.DeckComponent.DeckBaseImpl.{Deck, DeckFactory}
import de.htwg.se.skullking.model.DeckComponent.{IDeck, IDeckFactory}
import de.htwg.se.skullking.model.PlayerComponent.PlayerBaseImpl.{Hand, PlayerFactory}
import de.htwg.se.skullking.model.PlayerComponent.{IHand, IPlayer, IPlayerFactory}
import de.htwg.se.skullking.model.StateComponent.GameStateBaseImpl.GameState
import de.htwg.se.skullking.model.StateComponent.{IGameState, Phase}
import de.htwg.se.skullking.model.TrickComponent.TrickBaseImpl.Trick
import de.htwg.se.skullking.model.TrickComponent.TrickBonusPointsHandlerAlternateImpl.TrickBonusPointsHandler as FunnyHahaTrickBonusPointsHandler
import de.htwg.se.skullking.model.TrickComponent.TrickBonusPointsHandlerBaseImpl.TrickBonusPointsHandler as BaseImplTrickBonusPointsHandler
import de.htwg.se.skullking.model.TrickComponent.TrickWinnerHandlerBaseImpl.TrickWinnerHandler
import de.htwg.se.skullking.model.TrickComponent.{ITrick, ITrickBonusPointsHandler, ITrickWinnerHandler}

class DefaultModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind(classOf[IGameState]).to(classOf[GameState])
    bind(classOf[IController]).to(classOf[Controller])
    bind(classOf[IHand]).to(classOf[Hand])
    bind(classOf[IDeck]).to(classOf[Deck])
    bind(classOf[IPlayerFactory]).toInstance(PlayerFactory())
    bind(classOf[ITrick]).to(classOf[Trick])
    bind(classOf[ICardFactory]).toInstance(CardFactory())
    bind(classOf[IJokerCard]).to(classOf[JokerCard])
    bind(classOf[IDeckFactory]).toInstance(DeckFactory(CardFactory(), JokerCard()))
    bind(classOf[ITrickBonusPointsHandler]).to(classOf[BaseImplTrickBonusPointsHandler])
    bind(classOf[ITrickWinnerHandler]).to(classOf[TrickWinnerHandler])

    bind(new TypeLiteral[Phase] {}).toInstance(Phase.PrepareGame)
    bind(new TypeLiteral[JokerBehaviour] {}).toInstance(JokerBehaviour.Pirate)

    // default to empty list
    bind(new TypeLiteral[List[IDeck]] {}).toInstance(List())
    bind(new TypeLiteral[List[ITrick]] {}).toInstance(List())
    bind(new TypeLiteral[List[IPlayer]] {}).toInstance(List())
    bind(new TypeLiteral[List[ICard]] {}).toInstance(List())
    bind(new TypeLiteral[ List[(ICard, IPlayer)]] {}).toInstance(List())

    // default to 0
    bind(classOf[Integer]).toInstance(0)

    // non-zero default values
    bind(classOf[Integer]).annotatedWith(Names.named("roundLimit")).toInstance(10)
  }
}

//object Default {
//  given IGameState = GameState()
//  given IController = Controller(summon[IGameState])
//  given IHand = Hand()
//  given IDeck = Deck()
//  given IPlayerFactory = PlayerFactory
//  given ITrick = Trick()
//  given ICardFactory = CardFactory
//  given IJokerCard = JokerCard()
//  given IDeckFactory = DeckFactory
//
//  given baseImplBonusPointsHandler: ITrickBonusPointsHandler = BaseImplTrickBonusPointsHandler()
////  given funnyHahaBonusPointsHandler: ITrickBonusPointsHandler = FunnyHahaTrickBonusPointsHandler()
//  given ITrickWinnerHandler = TrickWinnerHandler()
//}
