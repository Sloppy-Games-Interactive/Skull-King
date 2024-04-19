val r1 = Card(Suit.Red, 1)
val r2 = Card(Suit.Red, 2)

val b1 = Card(Suit.Black, 1)

val special = Card(SpecialCard.Pirate, 0)

r1 < r2
r2 < r1
r1 == r1
r2.compare(b1)
b1.compare(r2)

special.compare(r1)
r1.compare(special)
special.compare(b1)
b1.compare(special)
