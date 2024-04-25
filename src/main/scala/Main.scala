import scala.annotation.tailrec
import scala.io.StdIn.readLine
import model._

@main def run(): Unit = {
  // create all playable cards as map
  val redCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Red, value))
  val blueCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Blue, value))
  val yellowCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Yellow, value))
  val blackCards: IndexedSeq[Card] = (1 to 14).map((value: Int) => Card(Suit.Black, value))
  val specialCards: Vector[Card] = Vector(Card(SpecialCard.Joker, 0), Card(SpecialCard.Mermaid, 0),
    Card(SpecialCard.SkullKing, 0), Card(SpecialCard.Pirate, 0), Card(SpecialCard.Escape, 0))

  // add all cards to one list
  val allCards: List[Card] = (redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList

  val fullDeck: Deck = Deck(allCards)
  
  
  val numPlayers = askHowManyPlayers()
  println(s"Cards: $fullDeck")
  println("Shuffling...")
  val shuffledDeck = fullDeck.shuffle()
  println(s"Shuffled deck: $shuffledDeck")
  println("")
  println("")
  var playerHands = createHandsForPlayers(numPlayers)

  var deck = shuffledDeck
  playerHands.zipWithIndex.foreach((h, i) => {
    val (newDeck, hand) = h.drawFromDeck(deck, 5)
    deck = newDeck
    playerHands = playerHands.updated(i, hand)

    println(s"model.Hand of Player ${i + 1}:")
    println(hand)
    println("")
  })
}

@tailrec
def askHowManyPlayers(): Int = {
  val minPlayers = 2
  val maxPlayers = 4
  
  print("How many players? ")
  val numPlayers = readLine().toInt
  if (numPlayers < minPlayers || numPlayers > maxPlayers) {
    println(s"Please enter a number between $minPlayers and $maxPlayers.")
    askHowManyPlayers()
  } else {
    numPlayers
  }
}

def createHandsForPlayers(numPlayers: Int): List[Hand] = {
  (1 to numPlayers).map(i => Hand()).toList
}
