package vanilla

case class Coordinate(row: Int, column: Int)

object Coordinate {
  implicit val ordering: Ordering[Coordinate] = Ordering.by(c => c.row -> c.column)
}