package com.devsuperior.crud.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceFoundException extends RuntimeException {

  public ResourceFoundException() {
    super();
  }

  public ResourceFoundException(String message) {
    super(message);
  }

  public ResourceFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceFoundException(Throwable cause) {
    super(cause);
  }

  protected ResourceFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
  
  
}
