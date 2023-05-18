package com.devsuperior.evento;

import com.devsuperior.evento.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventoApi {

  @Autowired
  private OrderService orderService;

  public static void main(String[] args) {
    SpringApplication.run(EventoApi.class, args);
  }


}
