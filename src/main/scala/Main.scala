import scala.annotation.tailrec
import scala.io.StdIn.readLine

@main def run(): Unit = {
  val numPlayers = askHowManyPlayers()
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
