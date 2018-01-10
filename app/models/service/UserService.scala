package models.service

import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.impl.User

trait UserService extends IdentityService[User] {
    def save(user: User): Option[User]
}