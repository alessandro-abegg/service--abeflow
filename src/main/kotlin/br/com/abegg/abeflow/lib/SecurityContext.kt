package br.com.abegg.abeflow.lib

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt


object SecurityContext {
    fun getAuthenticated(): Jwt? {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication != null && authentication.principal is Jwt) {
            return authentication.principal as Jwt?
        }

        return null
    }

    fun getOptionalUserId(): String? {
        return getAuthenticated()?.subject
    }

    fun getUserId(): String {
        return getAuthenticated()?.subject ?: throw IllegalStateException("User not authenticated")
    }

    fun getUsername(): String? {
        val jwt: Jwt? = getAuthenticated()
        return if (jwt != null) jwt.getClaimAsString("preferred_username") else "Anônimo"
    }
}