package com.devsuperior.evento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_participant")
public class Participant {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String name;
  @Column(unique = true)
  private String email;
  @Setter(AccessLevel.NONE)
  @ManyToMany(mappedBy = "participants")
  private Set<Activity> activities = new HashSet<>();

  public Participant() {
  }

  public Set<Activity> getActivities() {
    return activities;
  }

}
