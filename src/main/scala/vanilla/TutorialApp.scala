package vanilla

import org.scalajs.dom.document
import org.scalajs.jquery.jQuery

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

object TutorialApp extends JSApp {

  var currentPlayer: Player = Cross
  var game = TicTacToe.empty(3)

  def main(): Unit = {
    val gameTable = table(
      game.table.keys.toList.sorted.grouped(game.size).toList
        .map(row => tr(
          row.map(coordinate => td(
            button(
              id := cellId(coordinate),
              `class` := "cell",
              onclick := { () => onCellClick(coordinate) },
              "-"
            )
          ))
        )
      ))

    document.body.appendChild(gameTable.render)
  }

  def cellId(c: Coordinate): String =
    s"cell-${c.row}-${c.column}"

  @JSExport
  def onCellClick(coordinate: Coordinate): Unit = {
    jQuery(s"#${cellId(coordinate)}")
      .text(currentPlayer.draw)
       .attr("disabled", true)

    game.set(coordinate.row, coordinate.column)(currentPlayer)
      .fold(println(s"Invalid coordinate $coordinate"))(game = _)

    game.winner.foreach { player =>
      document.body.appendChild(p(s"Player $player won").render)
      jQuery(".cell").attr("disabled", true)
    }
    currentPlayer = currentPlayer.next
  }
}
