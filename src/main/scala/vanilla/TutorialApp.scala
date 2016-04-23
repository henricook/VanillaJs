package vanilla

import org.scalajs.dom.document
import org.scalajs.jquery.jQuery

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

object TutorialApp extends JSApp {

  var currentPlayer: Player = Cross
  var game = TicTacToe.empty

  def main(): Unit = {
    val gameTable = table(
      (0 to 2).map(row => tr(
        (0 to 2).map(column => td(
          button(
            id := s"cell-$row-$column",
            `class` := "cell",
            onclick := { () => onCellClick(row, column, s"cell-$row-$column") },
            "-"
          )
        ))
      ))
    )

    document.body.appendChild(gameTable.render)
  }

  @JSExport
  def onCellClick(row: Int, column: Int, id: String): Unit = {
    jQuery(s"#$id").text(currentPlayer.draw)
    jQuery(s"#$id").attr("disabled", true)

    game = game.set(row,column)(currentPlayer)
    game.winner.foreach { player =>
      document.body.appendChild(p(s"Player $player won").render)
      jQuery(".cell").attr("disabled", true)
    }
    currentPlayer = currentPlayer.next
  }
}
