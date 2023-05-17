package com.devsuperior.dscommerce;

import com.devsuperior.dscommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DsCommerceApi {

  @Autowired
  private OrderService orderService;

  public static void main(String[] args) {
    SpringApplication.run(DsCommerceApi.class, args);
  }


}
