package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.Category;
import lombok.Getter;

@Getter
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CategoryDTO {

  private Long id;
  private String name;

  public CategoryDTO(Long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public CategoryDTO(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }

}
