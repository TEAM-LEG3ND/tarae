package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder
import org.team_leg3nd.tarae.config.AppProperties
import org.team_leg3nd.tarae.domain.RedirectUri

@Service
class LoginService(
    private val appProperties: AppProperties,
    private val restTemplate: RestTemplate
) {
    fun login(): RedirectUri {
        val uriString = UriComponentsBuilder.fromHttpUrl(appProperties.login.oauthServerUrl)
            .queryParam("redirect_uri", appProperties.login.redirectUri)
            .toUriString()

        return try {
            restTemplate.getForObject<RedirectUri>(uriString)
        } catch (e: RestClientException) {
            throw RuntimeException("Internal Server Error: ${e.message}", e)
        }
    }
}