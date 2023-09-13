package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryDTO {

  private Long id;
  private String name;

  public CategoryDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public CategoryDTO(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }

}
