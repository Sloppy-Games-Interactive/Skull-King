import de.htwg.se.skullking.model._

val state = GameState()

// that's one way to sort cards when printing a player hand...
state.startNewRound.deck.shuffle().cards.sortBy(_.value).groupBy(_.suit).foreach(println)
