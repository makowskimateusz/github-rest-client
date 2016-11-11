package com.makowski.allegro.recruitment.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
@ControllerAdvice
public class NotFoundRepositoryOrUserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundRepositoryOrUserException.class, GithubApiTimeoutException.class, IOException.class})
    public ResponseEntity<Object> badRequest(RuntimeException ex, WebRequest request) {
          return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
