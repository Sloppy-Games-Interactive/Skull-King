import scala.annotation.tailrec
import scala.io.StdIn.readLine

@main def run(): Unit = {
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

    println(s"Hand of Player ${i + 1}:")
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
