package org.team_leg3nd.tarae.controller

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.RedirectURLResponseDto
import org.team_leg3nd.tarae.service.LoginService

@RestController
@RequestMapping("api")
class LoginController(
    private val loginService: LoginService
) {
    @GetMapping("/login")
    fun login(): ResponseEntity<RedirectURLResponseDto> {
        val result = loginService.login()
        return ResponseEntity.ok(RedirectURLResponseDto(result))
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 응답 코드 설정
    fun handleInternalServerError(e: RuntimeException): String {
        log.error("Internal Server Error", e)
        return "Internal Server Error"
    }
}