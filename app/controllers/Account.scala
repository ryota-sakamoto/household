package controllers

import javax.inject._
import com.mohiva.play.silhouette.api.Silhouette
import play.api.mvc.InjectedController

import silhouette.CookieEnv

@Singleton
class Account @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def loginIndex = silhouette.UnsecuredAction { implicit request =>
        Ok(views.html.account.login())
    }

    def login = TODO

    def logout = TODO
}