package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.respositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DataIntegrityViolationCustomException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.utils.ModelMapperUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  public ProductDTO findById(Long id) {
    Product product = this.repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(new ResourceNotFoundException()));

    return ModelMapperUtils.entityToDto(product, new ProductDTO().getClass());
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

  public ProductDTO update(Long id, ProductDTO requestDTO) {

    Optional<Product> entity = this.repository.findById(id);

    if (!entity.isPresent()) {
      throw new ResourceNotFoundException(new ResourceNotFoundException());
    }
    BeanUtils.copyProperties(requestDTO, entity, "id");

    Product result = this.repository.save(entity.get());
    return ModelMapperUtils.entityToDto(result, ProductDTO.class);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    try {
      this.repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Recurso não encontrado");
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationCustomException("Falha de integridade referencial");
    }

  }

  @Transactional(readOnly = true)
  public Page<ProductMinDTO> searchByPage(String name, Pageable pageable) {
    Page<Product> products = this.repository.searchByNamePageable(name, pageable);
    return products.map(ProductMinDTO::new);
  }

}
