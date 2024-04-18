import scala.util.Random

val immDeck = Deck(allCards)
val shuffled = immDeck.shuffle()
val (drawnCard, newDeck) = shuffled.draw()

drawnCard
newDeck
newDeck.draw()


val card1 = Card(Suit.Red, 1)
val printDeck = Deck(List(card1))