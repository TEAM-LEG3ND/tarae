package org.team_leg3nd.tarae.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ExpenseNotFoundException::class)
    fun handleExpenseNotFoundException(e: ExpenseNotFoundException): ResponseEntity<String> {
        logger.error { e.message }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(MemberNotFoundException::class)
    fun handleMemberNotFoundException(e: MemberNotFoundException): ResponseEntity<String> {
        logger.error { e.message }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(GroupNotFoundException::class)
    fun handleGroupNotFoundException(e: GroupNotFoundException): ResponseEntity<String> {
        logger.error { e.message }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalServerException(e: Exception): ResponseEntity<String> {
        logger.error { e.message }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error")
    }
}