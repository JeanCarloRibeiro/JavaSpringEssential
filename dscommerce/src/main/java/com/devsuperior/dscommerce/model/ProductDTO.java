package com.devsuperior.dscommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductDTO {

  private Long id;
  private String name;
  private String description;
  private double price;
  private String imgUrl;


}
