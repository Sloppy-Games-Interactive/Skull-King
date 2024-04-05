case class Card(value:Int, suit:String) {
  override def toString: String = {
    s"Card: $value $suit"
  }
}

val card = Card(10, "red")
println(card)