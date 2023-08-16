package com.devsuperior.uri2602.services;

import com.devsuperior.uri2602.entities.dto.CustomerDTO;
import com.devsuperior.uri2602.projections.CustomerProjection;
import com.devsuperior.uri2602.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public List<CustomerProjection> getCustomersByState(String state) {
    return this.customerRepository.findByState(state);
  }

  public List<CustomerDTO> getCustomersByStateJPQL(String state) {
    return this.customerRepository.findByStateJPQL(state);
  }


}
