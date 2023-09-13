package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class ClientDTO {

  private Long id;
  private String name;

  public ClientDTO(User user) {
    this.id = user.getId();
    this.name = user.getName();
  }

}
