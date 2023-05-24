package com.devsuperior.evento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "tb_category")
public class Category {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String description;

  @OneToMany(mappedBy = "category")
  private Set<Activity> activities;

  public Category() {
  }

  public Category(String description, Set<Activity> activities) {
    this.description = description;
    this.activities = activities;
  }

  public Set<Activity> getActivities() {
    return activities;
  }


}
