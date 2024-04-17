@main def run(): Unit =
  println("Yo ho ho ho!")
  println("Hallo!")
  println(s"played cards: $deck")

case class Deck(cards:List[Card])
val deck: Deck = Deck((redCards ++ blueCards ++ yellowCards ++ blackCards ++ specialCards).toList)
