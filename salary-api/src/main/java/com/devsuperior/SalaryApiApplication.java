package com.devsuperior;

import com.devsuperior.model.Order;
import com.devsuperior.services.OrderService;
import com.devsuperior.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class SalaryApiApplication implements CommandLineRunner {

  @Autowired
  private SalaryService salaryService;

  @Autowired
  private OrderService orderService;

  public static void main(String[] args) {
    SpringApplication.run(SalaryApiApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);

    Order order = new Order();

    System.out.println("Entre com o código do pedido: ");
    order.setCode(sc.nextInt());

    System.out.println("Entre com o valor do pedido: ");
    order.setBasic(sc.nextDouble());

    System.out.println("Entre com o percentual de deconto: ");
    order.setDiscount(sc.nextDouble());

    System.out.println("Saída...");
    System.out.printf("Pedido código: %d \nValor total: R$%.2f", order.getCode(), orderService.total(order));


  }

}
