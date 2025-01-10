package com.devsuperior.dscommerce.factory;

import com.devsuperior.dscommerce.model.Category;
import com.devsuperior.dscommerce.model.Product;

public class ProductFactory {

  public static Product createProduct() {
    Category category = CategoryFactory.createCategory();
    Product product = new Product(1L, "Senhor dos Anéis", "test", 300.00, "test");
    product.getCategories().add(category);
    return product;
  }

  public static Product createProduct(String name) {
    Category category = CategoryFactory.createCategory();
    Product product = new Product();
    product.setName(name);
    product.getCategories().add(category);
    return product;
  }

}
