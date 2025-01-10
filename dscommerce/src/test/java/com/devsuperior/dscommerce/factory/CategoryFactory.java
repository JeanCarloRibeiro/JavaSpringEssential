package com.devsuperior.dscommerce.factory;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.model.Category;

public class CategoryFactory {

  public static Category createCategory() {
    return new Category(1L, "Games");
  }

  public static CategoryDTO createCategoryDTO() {
    return new CategoryDTO(1L, "Games");
  }

}
