package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.model.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProduct(@PathVariable(value = "id") Long id) {
    ProductDTO productDTO = this.productService.findById(id);

    return ResponseEntity.ok(productDTO);
  }

  @GetMapping
  public ResponseEntity<Page<ProductDTO>> getProducts(Pageable pageable) {
    Page<ProductDTO> result = this.productService.findAll(pageable);

    return ResponseEntity.ok(result);
  }


}
