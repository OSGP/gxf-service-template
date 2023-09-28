/*
SPDX-FileCopyrightText: Contributors to the GXF project

SPDX-License-Identifier: Apache-2.0
*/
package org.gxf.servicetemplate.kafka.configuration.oauth

import com.microsoft.aad.msal4j.ClientCredentialFactory
import com.microsoft.aad.msal4j.ClientCredentialParameters
import com.microsoft.aad.msal4j.ConfidentialClientApplication
import org.apache.kafka.common.config.ConfigException
import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler
import org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule
import org.apache.kafka.common.security.oauthbearer.OAuthBearerToken
import org.apache.kafka.common.security.oauthbearer.OAuthBearerTokenCallback
import org.apache.kafka.common.security.oauthbearer.OAuthBearerValidatorCallback
import org.apache.kafka.common.security.oauthbearer.internals.secured.BasicOAuthBearerToken
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import javax.security.auth.callback.Callback
import javax.security.auth.callback.UnsupportedCallbackException
import javax.security.auth.login.AppConfigurationEntry

class OAuthAuthenticateCallbackHandler : AuthenticateCallbackHandler {
    private lateinit var tokenFilePath: String
    private lateinit var tokenEndpoint: String
    private lateinit var clientId: String
    private lateinit var scopes: Set<String>

    companion object {
        private val logger = LoggerFactory.getLogger(OAuthAuthenticateCallbackHandler::class.java)

        internal const val CLIENT_ID_CONFIG = "clientId"
        internal const val TOKEN_ENDPOINT_CONFIG = "tokenEndpoint"
        internal const val SCOPE_CONFIG = "scope"
        internal const val TOKEN_FILE_CONFIG = "tokenFile"
    }

    override fun configure(
        configs: Map<String?, *>?,
        saslMechanism: String,
        jaasConfigEntries: List<AppConfigurationEntry>
    ) {
        setFields(getOptions(saslMechanism, jaasConfigEntries))
    }

    private fun getOptions(
        saslMechanism: String, jaasConfigEntries: List<AppConfigurationEntry>
    ): Map<String, Any?> {
        require(saslMechanism == OAuthBearerLoginModule.OAUTHBEARER_MECHANISM) {
            "Unexpected SASL mechanism: ${saslMechanism}"
        }
        require(jaasConfigEntries.size == 1) {
            "Must supply exactly 1 non-null JAAS mechanism configuration (size was ${jaasConfigEntries.size})"
        }
        return jaasConfigEntries[0].options
    }

    private fun setFields(options: Map<String, Any?>) {
        clientId = options.getProperty(CLIENT_ID_CONFIG)
        tokenEndpoint = options.getProperty(TOKEN_ENDPOINT_CONFIG)
        scopes = options.getProperty(SCOPE_CONFIG)
            .split(",".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toSet()
        tokenFilePath = options.getProperty(TOKEN_FILE_CONFIG)
    }

    private fun Map<String, Any?>.getProperty(propertyName: String): String {
        if (!this.containsKey(propertyName)) {
            throw ConfigException(String.format("Kafka property: %s, not supplied", propertyName))
        }
        if (this[propertyName] == null) {
            throw ConfigException(String.format("Kafka property: %s, is null", propertyName))
        }
        if (this[propertyName] !is String) {
            throw ConfigException(String.format("Kafka property: %s, is not of type String", propertyName))
        }
        return this[propertyName] as String
    }

    override fun handle(callbacks: Array<Callback>) {
        for (callback in callbacks) {
            when (callback) {
                is OAuthBearerTokenCallback -> callback.token(getToken())
                is OAuthBearerValidatorCallback ->
                    throw UnsupportedCallbackException(callback, "Validate callback not yet implemented")
                else ->
                    throw UnsupportedCallbackException(callback, "Unknown callback type ${callback.javaClass.name}")
            }
        }
    }

    /** Retrieves a new JWT token from Azure Active Directory. */
    private fun getToken(): OAuthBearerToken {
        try {
            logger.debug("Retrieving Kafka OAuth Token")
            val token = readTokenFile(tokenFilePath)
            val credential = ClientCredentialFactory.createFromClientAssertion(token)
            val aadParameters = ClientCredentialParameters.builder(scopes).build()
            val aadClient = ConfidentialClientApplication.builder(clientId, credential)
                .authority(tokenEndpoint)
                .build()
            val authResult = aadClient.acquireToken(aadParameters).get()

            return BasicOAuthBearerToken(
                authResult.accessToken(),
                aadParameters.scopes(),
                authResult.expiresOnDate().toInstant().toEpochMilli(),
                aadClient.clientId(),
                System.currentTimeMillis()
            )
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw KafkaOAuthException("Retrieving JWT token was interrupted", e)
        } catch (e: Exception) {
            throw KafkaOAuthException("Caught an exception while retrieving JWT token", e)
        }
    }

    /**
     * Reads the content of the token file
     *
     * @return content of the token file
     */
    internal fun readTokenFile(tokenFilePath: String): String {
        try {
            val tokenFile = File(tokenFilePath)
            val bytes = Files.readAllBytes(tokenFile.toPath())
            val content = String(bytes, StandardCharsets.UTF_8)
            return content.dropLastWhile { it == '\n' }
        } catch (e: IOException) {
            throw KafkaOAuthException("Could not read Token file from: $tokenFilePath", e)
        }
    }

    override fun close() {
        // No need to close an oauth session, the token will expire automatically
    }
}
