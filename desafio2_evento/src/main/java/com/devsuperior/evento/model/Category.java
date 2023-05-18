package com.devsuperior.evento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tb_category")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Category {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String description;
  @OneToMany(mappedBy = "category")
  private Set<Activity> activities;

}
