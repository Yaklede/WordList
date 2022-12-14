package com.wordNote.wordNote.controller

import com.wordNote.wordNote.dto.error.ErrorCode
import com.wordNote.wordNote.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e : HttpRequestMethodNotSupportedException) : ResponseEntity<ErrorCode> {
        val response = ErrorCode(HttpStatus.METHOD_NOT_ALLOWED.value(),e.message.toString())
        return ResponseEntity(response,HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e : NotFoundException) : ResponseEntity<ErrorCode> {
        val response = ErrorCode(HttpStatus.NOT_FOUND.value(), e.message.toString())
        return ResponseEntity(response,HttpStatus.NOT_FOUND)
    }
}