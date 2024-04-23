package com.example.crudspring.crudspring.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestsExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(){
        var response = new ExceptionDTO("Dado não encontrado");
        return ResponseEntity.badRequest().body(response);
    }
}
