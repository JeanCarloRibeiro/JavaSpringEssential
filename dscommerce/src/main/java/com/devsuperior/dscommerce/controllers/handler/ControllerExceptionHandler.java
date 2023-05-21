package com.devsuperior.dscommerce.controllers.handler;

import com.devsuperior.dscommerce.model.dto.ApiCustomError;
import com.devsuperior.dscommerce.services.exceptions.DataIntegrityViolationCustomException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler  {

  @ExceptionHandler({ResourceNotFoundException.class})
  protected ResponseEntity<ApiCustomError> notFoundException(ResourceNotFoundException e, HttpServletRequest request) {
    ApiCustomError error = new ApiCustomError(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler({DataIntegrityViolationCustomException.class})
  protected ResponseEntity<Object> dataIntegrityViolationException(DataIntegrityViolationCustomException e, HttpServletRequest request) {
    ApiCustomError error = new ApiCustomError(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
