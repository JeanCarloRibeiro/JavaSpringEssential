package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.factory.CategoryFactory;
import com.devsuperior.dscommerce.model.Category;
import com.devsuperior.dscommerce.respositories.CategoryRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

  @InjectMocks
  private CategoryService service;
  @Mock
  private CategoryRepository repository;

  private Category category;
  private Long existingId;
  private Long nonExistingId;

  @BeforeEach
  void setUp() {
    category = CategoryFactory.createCategory();
    List<Category> categoryList = List.of(category);

    existingId = 1L;
    nonExistingId = 500L;

    Mockito.when(repository.findAll()).thenReturn(categoryList);
    Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(category));
    Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
  }

  @Test
  void findAllShouldReturnListCategoryDTO() {
    List<CategoryDTO> result = service.findAll();
    Assertions.assertEquals(result.size(), 1);
    Assertions.assertEquals(result.get(0).getId(), category.getId());
    Assertions.assertEquals(result.get(0).getName(), category.getName());
  }

  @Test
  void findByIdShouldReturnCategoryDTOWhenCategoryIdDoesExist() {
    CategoryDTO result = service.findById(existingId);
    Assertions.assertEquals(result.getId(), existingId);
  }

  @Test
  void findByIdShouldReturnCategoryDTOWhenCategoryIdDoesNonExist() {
    Assertions.assertThrows(ResourceNotFoundException.class,
            () -> service.findById(nonExistingId));
  }

}
