package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.factory.ProductFactory;
import com.devsuperior.dscommerce.model.Product;
import com.devsuperior.dscommerce.respositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DataIntegrityViolationCustomException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

  @InjectMocks
  private ProductService service;
  @Mock
  private ProductRepository repository;

  private Product product;
  private ProductDTO productDTO;
  private Long existingProductId;
  private Long nonExistingProductId;
  private Long dependentProductId;

  @BeforeEach
  void setUp() {
    product = ProductFactory.createProduct();
    productDTO = new ProductDTO(product);
    List<Product> productList = List.of(product);

    existingProductId = 1L;
    nonExistingProductId = 500L;
    dependentProductId = 3L;

    Page<Product> productPage = new PageImpl<>(productList);

    Mockito.when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
    Mockito.when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());
    Mockito.when(repository.findAll()).thenReturn(productList);
    Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(productPage);
    Mockito.when(repository.searchByNamePageable(anyString(), any(Pageable.class))).thenReturn(productPage);
    Mockito.when(repository.save(any())).thenReturn(product);

    Mockito.when(repository.getReferenceById(existingProductId)).thenReturn(product);
    Mockito.when(repository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);

    Mockito.when(repository.existsById(existingProductId)).thenReturn(true);
    Mockito.when(repository.existsById(dependentProductId)).thenReturn(true);
    Mockito.when(repository.existsById(nonExistingProductId)).thenReturn(false);

    Mockito.doNothing().when(repository).deleteById(existingProductId);
    Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentProductId);
  }

  @Test
  void findByIdShouldReturnProductDTOWhenProductIdExists() {
    ProductDTO result = service.findById(existingProductId);
    Assertions.assertEquals(result.getId(), existingProductId);
    Assertions.assertEquals(result.getName(), product.getName());
  }

  @Test
  void findByIdShouldReturnCategoryDTOWhenProductIdDoesNonExist() {
    Assertions.assertThrows(ResourceNotFoundException.class,
            () -> service.findById(nonExistingProductId));
  }

  @Test
  void findAllShouldReturnPagedProductDTO() {
    Pageable pageable = PageRequest.of(0, 12);
    Page<ProductDTO> result = service.findAll(pageable);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getSize(), 1);
    Assertions.assertEquals(result.iterator().next().getName(), product.getName());
  }

  @Test
  void searchByPageShouldReturnPagedProductMinDTO() {
    Pageable pageable = PageRequest.of(0, 12);
    Page<ProductMinDTO> result = service.searchByPage(product.getName(), pageable);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getSize(), 1);
    Assertions.assertEquals(result.iterator().next().getName(), product.getName());
  }

  @Test
  void saveShouldReturnProductDTO() {
    ProductDTO result = service.save(productDTO);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), product.getId());
    Assertions.assertEquals(result.getName(), product.getName());
  }

  @Test
  void updateShouldReturnProductDTO() {
    productDTO.setName("Test");

    ProductDTO result = service.update(existingProductId, productDTO);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getId(), product.getId());
    Assertions.assertEquals(result.getName(), "Test");
  }

  @Test
  void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
    Assertions.assertThrows(ResourceNotFoundException.class,
            () -> service.update(nonExistingProductId, productDTO));
  }

  @Test
  void deleteShouldDoNothingWhenIdExists() {
    Assertions.assertDoesNotThrow(() -> service.delete(existingProductId));
  }

  @Test
  void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
    Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingProductId));
  }

  @Test
  void deleteShouldThrowDataIntegrityViolationCustomExceptionWhenIdDoesNotExist() {
    Assertions.assertThrows(DataIntegrityViolationCustomException.class, () -> service.delete(dependentProductId));
  }

}
