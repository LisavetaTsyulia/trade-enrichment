package com.verygoodbank.tes.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class TradeEnrichmentControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handle(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("Failed to enrich trades", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}