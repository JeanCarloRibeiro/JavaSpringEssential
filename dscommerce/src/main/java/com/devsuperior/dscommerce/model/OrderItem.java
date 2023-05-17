package com.devsuperior.dscommerce.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "tb_order_item")
public class OrderItem {

  @EmbeddedId
  private OrderItemPk id = new OrderItemPk();
  private Integer quantity;
  private double price;

  public OrderItem(Order order, Product product, Integer quantity, double price) {
    this.id.setOrder(order);
    this.id.setProduct(product);
    this.quantity = quantity;
    this.price = price;
  }

  public Order getOrder() {
    return this.id.getOrder();
  }

  public void setOrder(Order order) {
    this.id.setOrder(order);
  }

  public Product getProduct() {
    return this.id.getProduct();
  }

  public void setProduct(Product product) {
    this.id.setProduct(product);
  }

  public Integer getQuantity() {
    return quantity;
  }

  public double getPrice() {
    return price;
  }


}
