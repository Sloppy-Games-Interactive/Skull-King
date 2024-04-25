class Hand(val cards: List[Card] = List()) {
  /**
   * Play nth card from hand
   * @param position
   * @return
   */
  def play(position: Int): (Card, Hand) = {
    val actualIdx = position - 1
    val (before, after) = cards.splitAt(actualIdx)
    (after.head, new Hand(before ++ after.tail))
  }
  def drawFromDeck(deck: Deck, n: Int): (Deck, Hand) = {
    val (drawn, remaining) = deck.draw(n)
    (remaining, new Hand(cards ++ drawn))
  }
  override def toString: String = {
    // print cards in hand as list like: 1: Red 1\n2: Red 2\n3: Black 1
    cards.zipWithIndex.map { case (card, idx) => s"${idx + 1}: $card" }.mkString("\n")
  }
}
