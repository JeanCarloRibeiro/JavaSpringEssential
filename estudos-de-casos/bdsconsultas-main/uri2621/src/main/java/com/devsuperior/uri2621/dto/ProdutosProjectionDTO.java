package com.devsuperior.uri2621.dto;

import com.devsuperior.uri2621.projections.ProdutosProjection;

public class ProdutosProjectionDTO {

  Long id;
  String name;
  Integer amount;

  public ProdutosProjectionDTO(ProdutosProjection p) {
    this.id = p.getId();
    this.name = p.getName();
    this.amount = p.getAmount();
  }

  public ProdutosProjectionDTO(Long id, String name, Integer amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "ProdutosProjectionDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", amount=" + amount +
            '}';
  }

}
