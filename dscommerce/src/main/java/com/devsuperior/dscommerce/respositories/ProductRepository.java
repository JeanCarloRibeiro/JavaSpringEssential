package com.devsuperior.dscommerce.respositories;

import com.devsuperior.dscommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query(value =
          "SELECT obj FROM Product obj JOIN FETCH obj.categories " +
          "WHERE (UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%')) OR :name IS NULL)",
          countQuery =
                  "SELECT count(obj) FROM Product obj JOIN obj.categories " +
                  "WHERE (UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%')) OR :name IS NULL)")
  Page<Product> searchByNamePageable(String name, Pageable pageable);

}
