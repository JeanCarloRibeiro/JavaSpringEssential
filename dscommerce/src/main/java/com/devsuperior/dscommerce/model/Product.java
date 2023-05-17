package com.devsuperior.dscommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_product")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Product {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  private double price;

  @Column(columnDefinition = "TEXT")
  private String imgUrl;

  @Setter(AccessLevel.NONE)
  @ManyToMany
  @JoinTable(name = "tb_product_category",
          joinColumns = @JoinColumn(name = "product_id"),
          inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  @OneToMany(mappedBy = "id.product")
  private Set<OrderItem> items = new HashSet<>();

  public List<Order> getOrders() {
    return this.items.stream().map(OrderItem::getOrder).collect(Collectors.toList());
  }

}
