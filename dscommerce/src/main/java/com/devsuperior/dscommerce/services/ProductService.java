package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.model.ProductDTO;
import com.devsuperior.dscommerce.respositories.ProductRepository;
import com.devsuperior.dscommerce.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  public ProductDTO findById(Long id) {
    Product product = this.repository.findById(id).orElse(null);
    ProductDTO productDto = new ProductDTO();

    return ModelMapperUtils.entityToDto(product, productDto.getClass());
  }

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAll(Pageable pageable) {
    Page<Product> result = this.repository.findAll(pageable);
    return result.map(x -> ModelMapperUtils.entityToDto(x, ProductDTO.class));
  }

  public ProductDTO save(ProductDTO request) {
    Product result = this.repository.save(ModelMapperUtils.dtoToEntity(request, Product.class));

    return ModelMapperUtils.entityToDto(result, ProductDTO.class);
  }

}
