package vanilla

sealed trait State extends Product with Serializable

case class Winner(player: Player) extends State
case object Draw extends State
case object Undecided extends State
