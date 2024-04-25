import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


val p1 = Hand()
val (newDeck, newP1) = p1.drawFromDeck(fullDeck.shuffle(), 5)
print("\n")
print("\n")
print(newP1)
print("\n")
print("\n")
val (playedCard, newHand) = newP1.play(2)
print("\n")
print("\n")
print(playedCard)
print("\n")
print("\n")
print(newHand)


//class Trick(val cards: List[Card] = List()) {
//  def play(card: Card): Trick = new Trick(cards :+ card)
//  def winner: Card = {
//    val leadSuit = cards.head.suit
//    // remove all cards that are not of the lead suit or trump or special
//    val filtered = cards.filter(c => c.suit == leadSuit || c.isTrump || c.isSpecial)
//
//    val hasSpecialOrTrump = filtered.exists(c => c.isSpecial || c.isTrump)
//    if !hasSpecialOrTrump then
//      filtered.max
//    else
//      val highestTrump = filtered.filter(c => c.isTrump).max
//      val highestSpecial = filtered.filter(c => c.isSpecial).max
//      if highestSpecial > highestTrump then highestSpecial else highestTrump
//  }
//  // def getBonusPoints: Int = 0 // TODO: implement
//}
//
//val r3 = Card(Suit.Red, 3)
//val b2 = Card(Suit.Black, 2)
//
//val t = new Trick().play(r2).play(special).play(r1).play(b2).play(r3)
//
//t.winner
