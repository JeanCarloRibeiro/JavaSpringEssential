package com.devsuperior.crud.controllers.handler;

import com.devsuperior.crud.dto.CustomErrorDTO;
import com.devsuperior.crud.dto.ValidationErrorDTO;
import com.devsuperior.crud.services.exceptions.DataIntegrityViolationCustomException;
import com.devsuperior.crud.services.exceptions.ResourceFoundException;
import com.devsuperior.crud.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler({ResourceNotFoundException.class})
  protected ResponseEntity<CustomErrorDTO> notFoundException(ResourceNotFoundException e, HttpServletRequest request) {
    CustomErrorDTO customError = new CustomErrorDTO(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
  }

  @ExceptionHandler({DataIntegrityViolationCustomException.class})
  protected ResponseEntity<CustomErrorDTO> dataIntegrityViolationException(DataIntegrityViolationCustomException e, HttpServletRequest request) {
    CustomErrorDTO customError = new CustomErrorDTO(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(customError);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<CustomErrorDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationErrorDTO customError = new ValidationErrorDTO(status, "Dados inválidos", req.getContextPath().toString());

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      String fieldName = error.getField();
      String msg = error.getDefaultMessage();

      customError.addError(fieldName, msg);
    }
    return ResponseEntity.status(status).body(customError);
  }

  @ExceptionHandler({ResourceFoundException.class})
  protected ResponseEntity<CustomErrorDTO> handleResourceFoundException(ResourceFoundException e, HttpServletRequest request) {
    CustomErrorDTO customError = new CustomErrorDTO(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(customError);
  }


}
