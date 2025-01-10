package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.model.Category;
import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.respositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DataIntegrityViolationCustomException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.utils.ModelMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Product product = this.repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(new ResourceNotFoundException()));

    return new ProductDTO(product);
  }

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAll(Pageable pageable) {
    Page<Product> result = this.repository.findAll(pageable);
    return result.map(x -> ModelMapperUtils.entityToDto(x, ProductDTO.class));
  }

  @Transactional(readOnly = true)
  public Page<ProductMinDTO> searchByPage(String name, Pageable pageable) {
    Page<Product> products = this.repository.searchByNamePageable(name, pageable);
    return products.map(ProductMinDTO::new);
  }

  @Transactional
  public ProductDTO save(ProductDTO request) {
    Product product = new Product();
    copyDtoToEntity(request, product);

    Product result = this.repository.save(product);

    System.out.println("ProductDTO save: " + result.toString());
    return new ProductDTO(result);
  }

  private void copyDtoToEntity(ProductDTO dto, Product entity) {
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setImgUrl(dto.getImgUrl());
    entity.getCategories().clear();

    for (CategoryDTO categoryDTO: dto.getCategories()) {
      Category category = new Category();
      category.setId(categoryDTO.getId());
      entity.getCategories().add(category);

    }
    System.out.println("CATEGORIA: " + entity.getCategories().toString());

  }

  @Transactional
  public ProductDTO update(Long id, ProductDTO requestDTO) {

    try {
      Product entity = this.repository.getReferenceById(id);
      copyDtoToEntity(requestDTO, entity);
      Product result = this.repository.save(entity);
      return new ProductDTO(result);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException(new ResourceNotFoundException());
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Recurso não encontrado");
    }
    try {
      this.repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationCustomException("Falha de integridade referencial");
    }

  }


}
