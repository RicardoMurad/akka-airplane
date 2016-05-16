import akka.actor.{Actor, ActorLogging}

object Altimeter {

  case class RateChange(amount: Float)

}

class Altimeter extends Actor with ActorLogging {

  implicit  val ec = context.dispatcher

  val ceiling = 43000

  val maxRateOfClimb = 5000

  var rateOfClimb = 0f

  var altitude = 0d

  var lastTick = System.currentTimeMillis

  val ticker = context.system.scheduler.schedule(100 milis, 100 milis, self, Tick)

  case object Tick

  def receive = {

    case RateChange(ammount) =>

      rateOfClimb = ammount.min(1.0f).max(-1.0f) * maxRateOfClimb
      log info(s"Altimeter changed rate of climb to $rateOfClimb")

    case Tick =>

      val tick = System.currentTimeMillis()
      altitude = altitude + ((tick - lastTick) / 60000.0) * rateOfClimb
      lastTick = tick
  }

  override def postStop() : Unit = ticker.cancel

}
