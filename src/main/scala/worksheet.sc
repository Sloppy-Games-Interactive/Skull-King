//class Trick(val cards: List[model.Card] = List()) {
//  def play(card: model.Card): Trick = new Trick(cards :+ card)
//  def winner: model.Card = {
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
//val r3 = model.Card(model.Suit.Red, 3)
//val b2 = model.Card(model.Suit.Black, 2)
//
//val t = new Trick().play(r2).play(special).play(r1).play(b2).play(r3)
//
//t.winner
