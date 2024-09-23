package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder
import org.team_leg3nd.tarae.config.AppProperty
import org.team_leg3nd.tarae.domain.RedirectUri

@Service
class LoginService(
    private val appProperty: AppProperty,
    private val restTemplate: RestTemplate
) {
    fun login(): RedirectUri {
        val uriString = UriComponentsBuilder.fromHttpUrl(appProperty.login.oauthServerUrl)
            .queryParam("redirect_uri", appProperty.login.redirectUri)
            .toUriString()

        return try {
            restTemplate.getForObject<RedirectUri>(uriString)
        } catch (e: RestClientException) {
            throw RuntimeException("Internal Server Error: ${e.message}", e)
        }
    }
}