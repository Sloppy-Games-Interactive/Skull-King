import scala.util.Random

case class Deck(cards:List[Card]) {
  var currentCards: List[Card] = cards
  def shuffle(): Unit = {
    currentCards = Random.shuffle(currentCards)
  }
  def draw(): Card = {
    val drawnCard = currentCards.last // get the last card
    currentCards = currentCards.dropRight(1) // remove the last card
    drawnCard
  }
}

val deck: Deck = Deck((redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList)
