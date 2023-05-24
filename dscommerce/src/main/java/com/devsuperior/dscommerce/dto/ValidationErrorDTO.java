package com.devsuperior.dscommerce.dto;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO extends CustomErrorDTO {

  private List<FieldMessageDTO> errors = new ArrayList<>();

  public ValidationErrorDTO(HttpStatus status, String error, String path) {
    super(status, error, path);
  }

  public List<FieldMessageDTO> getErrors() {
    return errors;
  }

  public void addError(String fieldName, String fieldMessage) {
    this.errors.add(new FieldMessageDTO(fieldName, fieldMessage));
  }

}
