import de.htwg.se.skullking.model._

trait PlayerId {
  val id: Int
}

class NPlayer(val id: PlayerId) extends Player {}

class Trick(val stack: List[(Card, PlayerId)] = List()) {
  def play(card: Card, id: PlayerId): Trick = new Trick(stack :+ (card, id))
  def winner: PlayerId = {
    val leadSuit = stack.head(0).suit
    // remove all cards that are not of the lead suit or trump or special
    val filtered = stack.filter((c, id) => c.suit == leadSuit || c.isTrump || c.isSpecial)

    val hasSpecialOrTrump = filtered.exists((c, id) => c.isSpecial || c.isTrump)
    if !hasSpecialOrTrump then
      // find tuple with highest value card of the lead suit

    else
      val highestTrump = filtered.filter((c, id) => c.isTrump).max
      val highestSpecial = filtered.filter((c, id) => c.isSpecial).max
      if highestSpecial > highestTrump then highestSpecial else highestTrump
  }
  // def getBonusPoints: Int = 0 // TODO: implement
}

val r1 = Card(Suit.Red, 1)
val r2 = Card(Suit.Red, 2)
val r3 = Card(Suit.Red, 3)
val b2 = Card(Suit.Black, 2)

val t = new Trick().play(r2).play(r1).play(b2).play(r3)

t.winner
