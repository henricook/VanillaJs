package vanilla

sealed trait Player extends Product with Serializable {
  def next: Player = this match {
    case Cross  => Circle
    case Circle => Cross
  }

  def draw: String = (this match {
    case Cross  => '\u2715'
    case Circle => '\u25CB'
  }).toString
}
case object Cross extends Player
case object Circle extends Player
