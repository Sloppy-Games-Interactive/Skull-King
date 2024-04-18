import scala.util.Random

val immDeck = Deck(allCards)
val shuffled = immDeck.shuffle()
val (drawnCard, newDeck) = shuffled.draw()

drawnCard
newDeck
newDeck.draw()


val card1 = Card(1, Suit.Red)
val printDeck = Deck(List(card1))