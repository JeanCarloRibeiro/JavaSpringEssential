package com.devsuperior.dscommerce.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class OrderItemPk {

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;


}
