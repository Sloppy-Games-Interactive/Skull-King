import scala.annotation.tailrec
import scala.io.StdIn.readLine
import model._

@main def run(): Unit = {
  var state = GameState()

  val numPlayers = askHowManyPlayers()

  (1 to numPlayers).foreach(i => {
    println(s"Enter player name: ")
    val name = readLine()
    val player = Player(name)
    state = state.addPlayer(player)
  })
  
  println(state.getStatusAsTable)
  state = state.startNewRound
  println(state.getStatusAsTable)
  state = state.dealCards
  println(state.getStatusAsTable)
}

//def askForPlayerNames(numPlayers: Int) = {
//  
//}

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

