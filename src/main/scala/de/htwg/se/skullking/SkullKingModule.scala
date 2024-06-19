package de.htwg.se.skullking

import net.codingwell.scalaguice.ScalaModule
import com.google.inject.{AbstractModule, Guice, Injector, Provides, TypeLiteral}
import de.htwg.se.skullking.controller.ControllerComponent.{Controller, IController}
import de.htwg.se.skullking.model.HandComponent.{Hand, IHand}
import de.htwg.se.skullking.model.StateComponent.{GameState, IGameState}

class SkullKingModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind(classOf[IController]).to(classOf[Controller])
  }
  
  @Provides
  def provideInitialGameState(): IGameState = GameState()
}

object Default {
  given state: IGameState = GameState()
  given IController = Controller()
  given hand: IHand = Hand()
}
