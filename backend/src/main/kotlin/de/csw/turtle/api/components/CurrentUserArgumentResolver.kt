package de.csw.turtle.api.components

import de.csw.turtle.api.CustomUserDetails
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.UserService
import org.springframework.core.MethodParameter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CurrentUserArgumentResolver(
    private val userService: UserService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.parameterType == UserEntity::class.java &&
                parameter.hasParameterAnnotation(AuthenticationPrincipal::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: return null

        val principal = authentication.principal as? CustomUserDetails
            ?: return null

        return userService.get(principal.userId)
    }

}