package com.devsuperior.dscommerce.controllers.handler;

import com.devsuperior.dscommerce.dto.CustomErrorDTO;
import com.devsuperior.dscommerce.dto.ValidationErrorDTO;
import com.devsuperior.dscommerce.services.exceptions.DataIntegrityViolationCustomException;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  private static HttpStatus httpStatus;

  @ExceptionHandler({ResourceNotFoundException.class})
  protected ResponseEntity<CustomErrorDTO> notFoundException(ResourceNotFoundException e, HttpServletRequest request) {
    httpStatus = HttpStatus.NOT_FOUND;
    CustomErrorDTO customError = new CustomErrorDTO(httpStatus, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(httpStatus).body(customError);
  }

  @ExceptionHandler({DataIntegrityViolationCustomException.class})
  protected ResponseEntity<CustomErrorDTO> dataIntegrityViolationException(DataIntegrityViolationCustomException e, HttpServletRequest request) {
    httpStatus = HttpStatus.CONFLICT;
    CustomErrorDTO customError = new CustomErrorDTO(httpStatus, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(httpStatus).body(customError);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<CustomErrorDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationErrorDTO customError = new ValidationErrorDTO(httpStatus, "Dados inválidos", req.getContextPath().toString());

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      String fieldName = error.getField();
      String msg = error.getDefaultMessage();
      customError.addError(fieldName, msg);
    }
    return ResponseEntity.status(httpStatus).body(customError);
  }

  @ExceptionHandler({ForbiddenException.class})
  protected ResponseEntity<CustomErrorDTO> forbiddenException(ForbiddenException e, HttpServletRequest request) {
    httpStatus = HttpStatus.FORBIDDEN;
    CustomErrorDTO customError = new CustomErrorDTO(httpStatus, e.getMessage(), request.getRequestURI().toString());
    return ResponseEntity.status(httpStatus).body(customError);
  }


}
