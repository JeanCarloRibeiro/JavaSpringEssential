package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.model.Category;
import com.devsuperior.dscommerce.respositories.CategoryRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public List<CategoryDTO> findAll() {
    List<Category> result = this.repository.findAll();
    return result.stream().map(x -> new CategoryDTO(x))
            .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
    Category result = this.repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(new ResourceNotFoundException()));

    return new CategoryDTO(result);
  }


}
