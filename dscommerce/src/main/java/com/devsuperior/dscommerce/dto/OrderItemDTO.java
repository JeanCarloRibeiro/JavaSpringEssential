package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDTO {

  private Long productId;
  private String name;
  private Double price;
  private Integer quantity;

  public OrderItemDTO(OrderItem orderItem) {
    this.productId = orderItem.getProduct().getId();
    this.name = orderItem.getProduct().getName();
    this.price = orderItem.getPrice();
    this.quantity = orderItem.getQuantity();
  }

  public Double getSubTotal() {
    return this.price * this.quantity;
  }

}
