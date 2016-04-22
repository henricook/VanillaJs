package vanilla

case class TicTacToe(private val table: Vector[Option[Player]]){
  def set(row: Int, column: Int)(player: Player): TicTacToe = {
    require(row <= 3 && row >= 0 && column <= 3 && column >= 0)
    TicTacToe(table.updated(3*row + column, Some(player)))
  }

  def get(row: Int, column: Int): Option[Player] =
    table(3*row + column)

  def winner: Option[Player] = {
    val combinations: Seq[Seq[(Int, Int)]] =
      (for {row <- 0 to 2} yield (row, row)) ::
        (for {row <- 0 to 2} yield (row, 2 - row)) ::
        (for {row <- 0 to 2; column <- 0 to 2} yield (row, column)).grouped(3).toList :::
        (for {row <- 0 to 2; column <- 0 to 2} yield (column, row)).grouped(3).toList

    combinations.map(_.map((get _).tupled)).map(winner(_))
      .collectFirst{ case Some(p) => p}
  }

  private def winner(cells: Seq[Option[Player]]): Option[Player] =
    cells.distinct.toList match {
      case x :: Nil => x
      case _ => None
    }
}

object TicTacToe {
  val empty = TicTacToe(Vector.fill(9)(None))
}
