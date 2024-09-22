package org.team_leg3nd.tarae.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "")
data class AppProperties(
    val server: Server = Server(),
    val login: Login = Login()
) {
    data class Server(
        var port: Int = 8080
    )

    data class Login(
        var oauthServerUrl: String = "",
        var redirectUri: String = ""
    )
}
