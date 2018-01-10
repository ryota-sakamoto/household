package silhouette

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.User
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator

trait CookieEnv extends Env {
    type I = User
    type A = CookieAuthenticator
}