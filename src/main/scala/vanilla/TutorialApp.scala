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

  @JSExport
  def onCellClick(coordinate: Coordinate): Unit = {
    jQuery(s"#${cellId(coordinate)}")
      .text(currentPlayer.draw)
       .attr("disabled", true)

    game.set(coordinate.row, coordinate.column)(currentPlayer)
      .fold(println(s"Invalid coordinate $coordinate"))(game = _)

    game.state match {
      case Winner(player) => stopGame(s"Player $player won")
      case Draw           => stopGame(s"It's a draw!")
      case Undecided      => ()
    }
    currentPlayer = currentPlayer.next
  }

  def cellId(c: Coordinate): String =
    s"cell-${c.row}-${c.column}"

  def stopGame(announcement: String): Unit = {
    document.body.appendChild(p(announcement).render)
    jQuery(".cell").attr("disabled", true)
  }
}
