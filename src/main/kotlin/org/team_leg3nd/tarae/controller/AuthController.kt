package org.team_leg3nd.tarae.controller

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.AccessTokenResponseDto
import org.team_leg3nd.tarae.controller.dto.RedirectUriResponseDto
import org.team_leg3nd.tarae.service.AuthService

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/login")
    fun login(): ResponseEntity<RedirectUriResponseDto> {
        val redirectUri = authService.login()

        return ResponseEntity.ok(RedirectUriResponseDto(redirectUri.redirectUri))
    }

    @GetMapping("/callback")
    fun callback(@RequestParam code: String): ResponseEntity<AccessTokenResponseDto> {
        val accessToken = authService.callback(code)

        return ResponseEntity.ok(AccessTokenResponseDto(accessToken.accessToken))
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 응답 코드 설정
    fun handleInternalServerError(e: RuntimeException): String {
        log.error("Internal Server Error", e)
        return "Internal Server Error"
    }
}