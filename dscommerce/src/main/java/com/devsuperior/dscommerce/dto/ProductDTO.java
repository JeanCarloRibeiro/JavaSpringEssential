package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
  @NotNull(message = "Campo requerido")
  @Positive(message = "O preço deve ser positivo")
  private double price;
  @NotEmpty
  private String imgUrl;
  @NotEmpty(message = "Deve ter pelo menos uma categoria")
  private List<CategoryDTO> categories = new ArrayList<>();

  public ProductDTO(Long id, String name, String description, double price, String imgUrl) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.imgUrl = imgUrl;
  }

  public ProductDTO(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.imgUrl = product.getImgUrl();
    this.categories = product.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toList());
  }

}
