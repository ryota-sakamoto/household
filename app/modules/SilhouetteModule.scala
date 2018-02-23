package modules

import javax.inject.Named

import com.google.inject.{AbstractModule, Provides}
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import com.mohiva.play.silhouette.api.crypto.{Crypter, CrypterAuthenticatorEncoder, Signer}
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.util.{Clock, FingerprintGenerator, IDGenerator}
import com.mohiva.play.silhouette.api.{Environment, EventBus, Silhouette, SilhouetteProvider}
import com.mohiva.play.silhouette.crypto.{JcaCrypter, JcaCrypterSettings, JcaSigner, JcaSignerSettings}
import com.mohiva.play.silhouette.impl.authenticators.{CookieAuthenticator, CookieAuthenticatorService, CookieAuthenticatorSettings}
import com.mohiva.play.silhouette.impl.util.{DefaultFingerprintGenerator, SecureRandomIDGenerator}
import net.codingwell.scalaguice.ScalaModule

import scala.concurrent.ExecutionContext.Implicits.global
import models.service._
import play.api.Configuration
import play.api.mvc.CookieHeaderEncoding
import silhouette.CookieEnv

// See https://github.com/mohiva/play-silhouette-seed/blob/master/app/modules/SilhouetteModule.scala
class SilhouetteModule extends AbstractModule with ScalaModule {
    def configure(): Unit = {
        bind[Silhouette[CookieEnv]].to[SilhouetteProvider[CookieEnv]]
        bind[UserService].to[UserServiceImpl]
        bind[CategoryService].to[CategoryServiceImpl]
        bind[AggregateDailyService].to[AggregateDailyServiceImpl]
        bind[FingerprintGenerator].toInstance(new DefaultFingerprintGenerator(false))
        bind[IDGenerator].toInstance(new SecureRandomIDGenerator())
        bind[Clock].toInstance(Clock())
    }

    @Provides
    def provideEnvironment(userService: UserService, authenticatorService: AuthenticatorService[CookieAuthenticator], eventBus: EventBus): Environment[CookieEnv] = {

        Environment[CookieEnv](
            userService,
            authenticatorService,
            Seq(),
            eventBus
        )
    }

    @Provides
    def provideAuthenticatorService(
                                       @Named("authenticator-signer") signer: Signer,
                                       @Named("authenticator-crypter") crypter: Crypter,
                                       cookieHeaderEncoding: CookieHeaderEncoding,
                                       fingerprintGenerator: FingerprintGenerator,
                                       idGenerator: IDGenerator,
                                       configuration: Configuration,
                                       clock: Clock): AuthenticatorService[CookieAuthenticator] = {

        val config = configuration.underlying.as[CookieAuthenticatorSettings]("silhouette.authenticator")
        val authenticatorEncoder = new CrypterAuthenticatorEncoder(crypter)

        new CookieAuthenticatorService(config, None, signer, cookieHeaderEncoding, authenticatorEncoder, fingerprintGenerator, idGenerator, clock)
    }

    @Provides @Named("authenticator-signer")
    def provideAuthenticatorSigner(configuration: Configuration): Signer = {
        val config = configuration.underlying.as[JcaSignerSettings]("silhouette.authenticator.signer")

        new JcaSigner(config)
    }

    @Provides @Named("authenticator-crypter")
    def provideAuthenticatorCrypter(configuration: Configuration): Crypter = {
        val config = configuration.underlying.as[JcaCrypterSettings]("silhouette.authenticator.crypter")

        new JcaCrypter(config)
    }

}