package com.fastcampus.web_ecommerce.exception;

import com.fastcampus.web_ecommerce.exception.custom.*;
import com.fastcampus.web_ecommerce.response.ErrorResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({
            BadRequestException.class,
            RoleNotFoundException.class,
            UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleBadRequestException(BadRequestException e) {
       log.error(e.getMessage());
        return ErrorResponse.builder()
               .code(HttpStatus.BAD_REQUEST.value())
               .message(e.getMessage())
               .timestamp(LocalDateTime.now())
               .build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleGenericException(Exception e, HttpServletResponse response) {

        if (e instanceof BadCredentialsException ||
                e instanceof AccountStatusException ||
                e instanceof AccessDeniedException ||
                e instanceof SignatureException ||
                e instanceof ExpiredJwtException ||
                e instanceof AuthenticationException ||
                e instanceof InsufficientAuthenticationException
        ) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return ErrorResponse.builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .message(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        log.error(e.getMessage());
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String errorMessage = objectError.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(String.join(", ", errors.values()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorResponse handleInvalidPasswordException(InvalidPasswordException e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({
            UsernameAlreadyExistsException.class,
            EmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleConfictException(UsernameAlreadyExistsException e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ErrorResponse handleForbiddenException(ForbiddenException e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(RequestNotPermitted.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public @ResponseBody ErrorResponse handleRateLimitException(RequestNotPermitted e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .code(HttpStatus.TOO_MANY_REQUESTS.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
