package com.devsuperior.dscommerce.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductDTO {

  private Long id;
  @NotEmpty
  private String name;
  @NotEmpty
  private String description;
  @Range(min = 0L)
  private double price;
  @NotEmpty
  private String imgUrl;


}
