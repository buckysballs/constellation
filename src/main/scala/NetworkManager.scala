import com.typesafe.config.Config
import scorex.core.api.http.{ApiRoute, PeersApiRoute, UtilsApiRoute}
import scorex.core.network.message._
import scorex.core.settings.ScorexSettings
import scorex.core.settings.ScorexSettings.readConfigFromPath
import scorex.core.utils.ScorexLogging

import scala.language.postfixOps

/**
  * Created by Wyatt on 11/11/17.
  */

class NetworkManager(settingsDir: String) extends ApplicationMod {

  val settings: ScorexSettings = NetworkManager.read(Some(settingsDir))
  val apiTypes: Set[Class[_]] = Set(classOf[UtilsApiRoute], classOf[PeersApiRoute])

  // we want to add history and other message types here
  val additionalMessageSpecs: Seq[MessageSpec[_]] = Seq()
  // Turn this info a method
  val basicSpecs = {
    val invSpec = new InvSpec(settings.network.maxInvObjects)
    val requestModifierSpec = new RequestModifierSpec(settings.network.maxInvObjects)
    Seq(
      GetPeersSpec,
      PeersSpec,
      invSpec,
      requestModifierSpec,
      ModifiersSpec
    )
  }
  
  val apiRoutes: Seq[ApiRoute] = Seq(
    UtilsApiRoute(settings.restApi),
    PeersApiRoute(peerManagerRef, networkController, settings.restApi)
  )
}

object NetworkManager extends ScorexLogging {
    def read(userConfigPath: Option[String]): ScorexSettings = {
      fromConfig(readConfigFromPath(userConfigPath, "scorex"))
    }

//    implicit val networkSettingsValueReader: ValueReader[ScorexSettings] =
//      (cfg: Config, path: String) => fromConfig(cfg.getConfig(path))

  def fromConfig(config: Config): ScorexSettings = {
      log.info(config.toString)
      //TODO this is totally broken, im 90% sure that scala version diff is causing issues with the implicit above
      config.asInstanceOf[ScorexSettings]//("scorex")
    }

  def main(args: Array[String]): Unit = {
    val settingsFilename = args.headOption.getOrElse("settings.conf")
    new NetworkManager(settingsFilename).run()
  }
}