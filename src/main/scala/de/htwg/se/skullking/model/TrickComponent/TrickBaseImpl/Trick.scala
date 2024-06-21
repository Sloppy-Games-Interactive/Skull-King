package de.htwg.se.skullking.model.TrickComponent.TrickBaseImpl

//import de.htwg.se.skullking.modules.Default.given
import com.google.inject.Inject

import de.htwg.se.skullking.model.CardComponent.{ICard, Suit}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer
import de.htwg.se.skullking.model.TrickComponent.*

class Trick @Inject(trickWinnerHandler: ITrickWinnerHandler, trickBonusPointsHandler: ITrickBonusPointsHandler) (
  val stack: List[(ICard, IPlayer)] = List()
) extends ITrick {
  def cards: List[ICard] = stack.map((card, player) => card)
  
  def players: List[IPlayer] = stack.map((card, player) => player)
  
  def leadSuit: Suit|Any = cards match {
    // first suit leads the trick
    case suitLead if suitLead.nonEmpty && !suitLead.head.isSpecial => suitLead.head.suit
    // escape was played first -> first suit after escape leads the trick
    case escape if escape.nonEmpty && escape.head.suit == Suit.Escape => escape.collectFirst({ case c if !c.isSpecial => c.suit }).getOrElse(None)
    // special card was played first -> no lead suit
    case _ => None
  }
  
  def hasSkullKing: Boolean = cards.exists(c => c.suit == Suit.SkullKing)
  
  def hasPirate: Boolean = cards.exists(c => c.suit == Suit.Pirate)
  
  def hasMermaid: Boolean = cards.exists(c => c.suit == Suit.Mermaid)
  
  def play(card: ICard, player: IPlayer): ITrick = Trick(trickWinnerHandler, trickBonusPointsHandler)(stack :+ (card, player))
  
  def winner: Option[IPlayer] = trickWinnerHandler.handle(this)
  
  def calculateBonusPoints: Int = trickBonusPointsHandler.handle(this)

  override def toString: String = {
    "Trick: " + stack.map((card, player) => s"${player.name}: $card").mkString("; ")
  }
}
