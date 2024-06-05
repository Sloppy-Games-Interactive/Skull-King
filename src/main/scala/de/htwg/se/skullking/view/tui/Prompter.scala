package de.htwg.se.skullking.view.tui

import de.htwg.se.skullking.model.player.Player

class Prompter {
  def promptPlayerLimit: Unit = {
    println("Enter player count:")
  }

  def promptPlayerName: Unit = {
    println("Enter player name:")
  }

  def promptPrediction(playerName: String, round: Int): Unit = {
    println(s"$playerName, enter your prediction for round $round:")
  }

  def promptCardPlay(player: Player): Unit = {
    println(s"${player.name}, play your card:")
    println(player.hand)
  }
}
