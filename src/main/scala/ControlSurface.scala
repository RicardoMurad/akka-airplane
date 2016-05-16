                                    import _root_.Altimeter.RateChange
import akka.actor.{Actor, ActorRef}

object ControlSurface {

  case class  StickBack(amount: Float)
  case class  StickFoward(amount: Float)

}


class ControlSurface(altimeter: ActorRef) extends Actor {

  import ControlSurface._

  def receive = {

    case StickBack(amount) => altimeter ! RateChange(amount)
    case StickFoward(amount) => altimeter ! RateChange(-1 * amount)

  }

}
