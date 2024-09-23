package org.team_leg3nd.tarae.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "")
class AppProperty(
    val server: ServerProperty,
    val auth: AuthProperty
) {
    data class ServerProperty(
        var port: Int
    )

    data class AuthProperty(
        var host: String = "",
        var path: AuthPath = AuthPath(),
        var redirectUri: String = ""
    ) {
        data class AuthPath(
            var login: String = "",
            var callback: String = ""
        )
    }
}
