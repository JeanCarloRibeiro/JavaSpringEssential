package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  OrderService service;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
  @GetMapping("/{id}")
  public ResponseEntity<OrderDTO> getOrder(@PathVariable(value = "id") Long id) {
    OrderDTO orderDTO = this.service.findById(id);

    return ResponseEntity.ok(orderDTO);
  }

  @PreAuthorize("hasRole('ROLE_CLIENT')")
  @PostMapping
  public ResponseEntity<OrderDTO> saveOrder(@Valid @RequestBody OrderDTO request) {
    OrderDTO orderDTO = this.service.saveOrder(request);

    return ResponseEntity.ok(orderDTO);
  }




}
