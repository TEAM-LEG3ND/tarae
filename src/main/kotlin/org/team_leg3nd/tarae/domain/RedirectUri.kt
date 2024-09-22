package org.team_leg3nd.tarae.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedirectUri(
    @SerialName("redirect_uri")
    val redirectUri: String
)
