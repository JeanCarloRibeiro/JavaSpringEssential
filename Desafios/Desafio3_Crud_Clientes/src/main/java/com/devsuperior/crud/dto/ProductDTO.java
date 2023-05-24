package com.devsuperior.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductDTO {

  private Long id;
  @Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
  @NotBlank(message = "Campo requerido")
  private String name;
  @Size(min = 10, message = "Descrição precisa ter no mínimo 10 caracteres")
  @NotBlank
  private String description;
  @Positive(message = "O preço deve ser positivo")
  private double price;
  @NotEmpty
  private String imgUrl;


}
