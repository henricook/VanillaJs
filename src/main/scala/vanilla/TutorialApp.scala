package vanilla

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.jquery.{JQueryEventObject, jQuery}

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
            `class` := "cell",
            "data-row".attr := row,
            "data-column".attr := column,
            "-"
          )
        ))
      ))
    )

    document.body.appendChild(gameTable.render)
    jQuery(".cell").click(handler _)
  }

  @JSExport
  def handler(o: JQueryEventObject): Unit = {
    jQuery(o.delegateTarget).parent().text(currentPlayer.draw)
    game = game.set(
      jQuery(o.delegateTarget).attr("data-row").fold(-1)(_.toInt),
      jQuery(o.delegateTarget).attr("data-column").fold(-1)(_.toInt)
    )(currentPlayer)
    game.winner.foreach { p =>
      appendPar(document.body, s"Player $p won")
      jQuery(".cell").remove()

    }

    currentPlayer = currentPlayer.next
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }
}
