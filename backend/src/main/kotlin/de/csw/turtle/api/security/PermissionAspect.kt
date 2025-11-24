package de.csw.turtle.api.security

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.security.InsufficientPermissionException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class PermissionAspect {

    @Around("@annotation(RequiresPermission)")
    fun checkPermission(joinPoint: ProceedingJoinPoint): Any? {
        val method = (joinPoint.signature as org.aspectj.lang.reflect.MethodSignature).method
        val requiresPermission = method.getAnnotation(RequiresPermission::class.java)!!

        val principal = SecurityContextHolder.getContext().authentication?.principal
        val role = if (principal is UserEntity) principal.role else Role.GUEST
        val permission = requiresPermission.permission

        if (!role.permissions.contains(permission))
            throw InsufficientPermissionException(permission)

        return joinPoint.proceed()
    }

}