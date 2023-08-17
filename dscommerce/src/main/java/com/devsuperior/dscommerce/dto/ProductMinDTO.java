package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter @Setter
public class ProductMinDTO {

  private Long id;
  private String name;
  private double price;
  private String imgUrl;
  @NotEmpty
  private List<CategoryDTO> categories = new ArrayList<>();

  public ProductMinDTO(Long id, String name, double price, String imgUrl) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.imgUrl = imgUrl;
  }

  public ProductMinDTO(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.price = product.getPrice();
    this.imgUrl = product.getImgUrl();
    this.categories = product.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toList());
  }



}
