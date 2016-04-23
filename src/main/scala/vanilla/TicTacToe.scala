package vanilla

case class TicTacToe private(size: Int, table: Map[Coordinate, Option[Player]]){
  def get(row: Int, column: Int): Option[Player] =
    table.get(Coordinate(row, column)).flatten

  def set(row: Int, column: Int)(player: Player): Option[TicTacToe] =
    table.get(Coordinate(row, column)).map(_ =>
      TicTacToe(size, table + (Coordinate(row, column) -> Some(player)))
    )

  def state: State =
    winner.fold(
      if(table.values.forall(_.isDefined)) Draw else Undecided
    )(Winner)

  def winner: Option[Player] =
    rowsColumnsDiags
      .map(_.map((get _).tupled))
      .map(_.distinct.toList match {
        case x :: Nil => x
        case _        => None
      }).collectFirst{ case Some(p) => p }

  private val rowsColumnsDiags: Seq[Seq[(Int, Int)]] = {
    val maxIndex = size - 1
    (for {row <- 0 to maxIndex} yield (row, row)) ::
      (for {row <- 0 to maxIndex} yield (row, maxIndex - row)) ::
      (for {row <- 0 to maxIndex; column <- 0 to maxIndex} yield (row, column)).grouped(size).toList :::
      (for {row <- 0 to maxIndex; column <- 0 to maxIndex} yield (column, row)).grouped(size).toList
  }
}

object TicTacToe {
  def empty(size: Int): TicTacToe =
    TicTacToe(
      size,
      (for {
        row    <- 0 until size
        column <- 0 until size
      } yield Coordinate(row, column) -> Option.empty[Player]).toMap
    )
}