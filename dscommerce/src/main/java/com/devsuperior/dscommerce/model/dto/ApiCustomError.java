package com.devsuperior.dscommerce.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiCustomError {

  private LocalDateTime timestamp;
  private Integer status;
  private String error;
  private String path;

  public ApiCustomError(LocalDateTime timestamp) {
    super();
    this.timestamp = timestamp;
  }

  public ApiCustomError(HttpStatus status, String error, String path) {
    super();
    this.timestamp = LocalDateTime.now();
    this.status = status.value();
    this.error = error;
    this.path = path;
  }

}
