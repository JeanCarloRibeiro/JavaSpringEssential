package com.devsuperior.uri2602.entities.dto;

import com.devsuperior.uri2602.projections.CustomerProjection;

public class CustomerDTO {
  String name;

  public CustomerDTO(CustomerProjection c) {
    this.name = c.getName();
  }

  public CustomerDTO(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "CustomerDTO{" +
            "name='" + name + '\'' +
            '}';
  }
}
