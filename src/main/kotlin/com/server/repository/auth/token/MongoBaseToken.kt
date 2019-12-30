package com.server.repository.auth.token

import com.server.repository.MongoObject
import com.server.repository.auth.AuthenticationSerializer
import org.springframework.security.oauth2.provider.OAuth2Authentication

abstract class MongoBaseToken(
        /** The token value itself */
        var token: String,
        /** The serialized Authentication object */
        private val auth: String?,
        val username: String?,
        val clientId: String?) : MongoObject() {

    /**
     * @return the de-serialized Authentication object
     */
    val authentication: OAuth2Authentication?
        get() = AuthenticationSerializer.deserialize(auth)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MongoBaseToken

        if (token != other.token) return false
        if (username != other.username) return false
        if (clientId != other.clientId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = token.hashCode()
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (clientId?.hashCode() ?: 0)
        return result
    }
}