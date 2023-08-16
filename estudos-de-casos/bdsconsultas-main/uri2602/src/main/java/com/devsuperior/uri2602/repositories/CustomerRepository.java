package com.devsuperior.uri2602.repositories;

import com.devsuperior.uri2602.entities.Customer;
import com.devsuperior.uri2602.entities.dto.CustomerDTO;
import com.devsuperior.uri2602.projections.CustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query(nativeQuery = true, value = "SELECT name FROM customers WHERE UPPER(state) = UPPER(:state)")
  List<CustomerProjection> findByState(String state);

  @Query("SELECT new com.devsuperior.uri2602.entities.dto.CustomerDTO(obj.name) " +
          "FROM Customer obj " +
          "WHERE UPPER(obj.state) = UPPER(:state)")
  List<CustomerDTO> findByStateJPQL(String state);

}
