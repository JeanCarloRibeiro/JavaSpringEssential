package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderItemDTO {

  private Long productId;
  private String name;
  private Double price;
  private Integer quantity;
  private String imgUrl;

  public OrderItemDTO(OrderItem orderItem) {
    this.productId = orderItem.getProduct().getId();
    this.name = orderItem.getProduct().getName();
    this.price = orderItem.getPrice();
    this.quantity = orderItem.getQuantity();
    this.imgUrl = orderItem.getProduct().getImgUrl();
  }

  public Double getSubTotal() {
    return this.price * this.quantity;
  }

}
