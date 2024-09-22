package org.team_leg3nd.tarae.controller.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedirectUriResponseDto(
    @SerialName("redirect_uri")
    val redirectUri: String
)
