package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import org.team_leg3nd.tarae.config.AppProperty
import org.team_leg3nd.tarae.domain.RedirectUri

@Service
class LoginService(
    private val appProperty: AppProperty,
    private val webClientBuilder: WebClient.Builder
) {
    fun login(): RedirectUri {
        return try {
            webClientBuilder.build()
                .get()
                .uri(
                    UriComponentsBuilder.fromHttpUrl(appProperty.login.oauthServerUrl)
                        .queryParam("redirect_uri", appProperty.login.redirectUri)
                        .toUriString()
                )
                .retrieve()
                .bodyToMono(RedirectUri::class.java)
                .block() ?: throw RestClientException("Response is null")
        } catch (e: RestClientException) {
            throw RuntimeException("Internal Server Error: ${e.message}", e)
        }
    }
}