package de.htwg.se.skullking.model.TrickComponent

import de.htwg.se.skullking.model.CardComponent.{StandardCard, Suit}
import de.htwg.se.skullking.model.PlayerComponent.IPlayer

trait WinnerHandler {
  def handle(t: ITrick): Option[IPlayer]
}

/**
 * if all cards are escape, first played wins the trick
 */
class winnerAllEscapeWinnerHandler extends WinnerHandler {
  override def handle(t: ITrick): Option[IPlayer] = t.stack match {
    case allEscape if allEscape.nonEmpty && allEscape.forall((c, p) => c.suit == Suit.Escape) => Some(t.players.head)
    case _ => None
  }
}

/**
 * <b>Mermaid conditions:</b><br>
 * - mermaid loses to pirate, if no skull king is present<br>
 * - mermaid beats all suits and skull king<br>
 * - if skull king and pirate are present, mermaid wins<br>
 * - if multiple mermaids were played, first one wins<br>
 * <br>
 * <b>Pirate conditions:</b><br>
 * - pirate loses to skull king<br>
 * - pirate beats all suits and mermaids<br>
 * <br>
 * <b>Skull King conditions:</b><br>
 * - only skull king remains<br>
 * - skull king beats all suits and pirates<br>
 */
class winnerSpecialWinnerHandler extends WinnerHandler {
  override def handle(t: ITrick): Option[IPlayer] = t.stack.filter((c, p) => c.suit != Suit.Escape) match {
    case withSpecial if withSpecial.exists((c, p) => c.isSpecial) => {
      withSpecial.filter((c, p) => c.isSpecial) match {
        case mermaidWin if (t.hasMermaid && t.hasSkullKing) || (t.hasMermaid && !t.hasPirate) => Some(mermaidWin.head._2)
        case pirateWin if !t.hasSkullKing && t.hasPirate => Some(pirateWin.head._2)
        case _ => Some(withSpecial.collectFirst({ case (c, p) if c.suit == Suit.SkullKing => p }).get)
      }
    }
    case _ => None
  }
}

/**
 * if a trump card was played, the highest trump card wins the trick
 */
class winnerTrumpWinnerHandler extends WinnerHandler {
  override def handle(t: ITrick): Option[IPlayer] = t.stack match {
    case withTrump if withTrump.exists((c, p) => c.isTrump) => {
      Some(withTrump.collect{
        case (c: StandardCard, p) => (c, p)
      }.filter((c, p) => c.isTrump).maxBy((c, p) => c.value)._2)
    }
    case _ => None
  }
}

/**
 * if no trump card was played, the highest card of the lead suit wins the trick
 */
class winnerLeadSuitWinnerHandler extends WinnerHandler {
  override def handle(t: ITrick): Option[IPlayer] = t.stack match {
    case withLeadSuit if withLeadSuit.exists((c, p) => c.suit == t.leadSuit) => {
      Some(withLeadSuit.collect{
        case (c: StandardCard, p) => (c, p)
      }.filter((c, p) => c.suit == t.leadSuit).maxBy((c, p) => c.value)._2)
    }
    case _ => None
  }
}
