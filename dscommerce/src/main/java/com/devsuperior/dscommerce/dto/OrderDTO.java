package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.enums.OrderStatus;
import com.devsuperior.dscommerce.model.Order;
import com.devsuperior.dscommerce.model.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @Getter
public class OrderDTO {

  private Long id;
  private Instant moment;
  private OrderStatus status;
  private ClientDTO client;
  private PaymentDTO payment;
  private List<OrderItemDTO> items = new ArrayList<>();

  public OrderDTO(Order order) {
    this.id = order.getId();
    this.moment = order.getMoment();
    this.status = order.getStatus();
    this.client = new ClientDTO(order.getClient());
    this.payment = order.getPayment() == null ? null : new PaymentDTO(order.getPayment());
    for (OrderItem item : order.getItems()) {
      this.items.add(new OrderItemDTO(item));
    }


  }

  public Long getId() {
    return id;
  }

  public Instant getMoment() {
    return moment;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public ClientDTO getClient() {
    return client;
  }

  public PaymentDTO getPayment() {
    return payment;
  }

  public List<OrderItemDTO> getItems() {
    return items;
  }

  public Double getTotal() {
    Double sum = 0.0;
    for (OrderItemDTO item : getItems()) {
      sum += item.getSubTotal();
    }
    return sum;
  }

}
