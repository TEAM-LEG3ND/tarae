package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.reactive.function.client.WebClient
import org.team_leg3nd.tarae.config.AppProperty
import org.team_leg3nd.tarae.domain.AccessToken
import org.team_leg3nd.tarae.domain.RedirectUri

@Service
class AuthService(
    private val appProperty: AppProperty,
    private val webClientBuilder: WebClient.Builder
) {
    fun login(): RedirectUri {
        return try {
            webClientBuilder.baseUrl(appProperty.auth.host).build()
                .get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path(appProperty.auth.path.login)
                        .queryParam("redirect_uri", appProperty.auth.redirectUri)
                        .build()
                }
                .retrieve()
                .bodyToMono(RedirectUri::class.java)
                .block() ?: throw RestClientException("Response is null")
        } catch (e: RestClientException) {
            throw RuntimeException("Internal Server Error: ${e.message}", e)
        }
    }

    fun callback(code: String): AccessToken {
        return try {
            webClientBuilder.baseUrl(appProperty.auth.host).build()
                .get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path(appProperty.auth.path.callback)
                        .queryParam("code", code)
                        .build()
                }
                .retrieve()
                .bodyToMono(AccessToken::class.java)
                .block() ?: throw RestClientException("Response is null")
        } catch (e: RestClientException) {
            throw RuntimeException("Internal Server Error: ${e.message}", e)
        }
    }
}