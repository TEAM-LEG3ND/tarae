package org.team_leg3nd.tarae.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@ConfigurationProperties(prefix = "")
class AppProperty(
    val server: ServerProperty,
    val login: LoginProperty
) {
    data class ServerProperty(
        var port: Int
    )

    data class LoginProperty(
        var oauthServerUrl: String = "",
        var redirectUri: String = ""
    )

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
