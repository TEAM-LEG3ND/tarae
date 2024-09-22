package org.team_leg3nd.tarae.controller

import kotlinx.serialization.json.Json
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.RedirectUriResponseDto
import org.team_leg3nd.tarae.service.LoginService

@RestController
@RequestMapping("api")
class LoginController(
    private val loginService: LoginService
) {
    @GetMapping("/login")
    fun login(): ResponseEntity<RedirectUriResponseDto> {
        val result = loginService.login()

        return ResponseEntity.ok(Json.decodeFromString<RedirectUriResponseDto>(result))
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 응답 코드 설정
    fun handleInternalServerError(e: RuntimeException): String {
        log.error("Internal Server Error", e)
        return "Internal Server Error"
    }
}