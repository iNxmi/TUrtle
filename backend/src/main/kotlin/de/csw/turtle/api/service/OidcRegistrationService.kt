package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.entity.ExternalIdentityEntity
import de.csw.turtle.api.repository.ExternalIdentityRepository
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class OidcRegistrationService(
    private val userService: UserService,
    private val externalIdentityRepository: ExternalIdentityRepository //TODO change to service
) : OAuth2UserService<OidcUserRequest, OidcUser> {

    private val delegate = OidcUserService()

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidc = delegate.loadUser(userRequest)
        val issuer = oidc.issuer.toString()
        val subject = oidc.subject

        externalIdentityRepository.findByIssuerAndSubject(issuer, subject)?.let {
            return object : OidcUser by oidc {
                override fun getName(): String = it.user.username
            }
        }

        val email = oidc.email
        val user = userService.create(
            CreateUserRequest(
                username = email ?: subject,
                firstName = oidc.givenName ?: "",
                lastName = oidc.familyName ?: "",
                email = email ?: "$subject@$issuer",
                password = "", // OIDC users have no password
            )
        )

        externalIdentityRepository.save(ExternalIdentityEntity(user, issuer, subject))

        return object : OidcUser by oidc {
            override fun getName(): String = user.username
        }
    }

}