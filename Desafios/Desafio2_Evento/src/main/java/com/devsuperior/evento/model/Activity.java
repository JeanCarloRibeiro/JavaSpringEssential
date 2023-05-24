package com.devsuperior.evento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_activity")
public class Activity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String name;
  @Column
  private String description;
  @Column
  private double price;
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  @Setter(AccessLevel.NONE)
  @ManyToMany
  @JoinTable(name = "tb_activity_participant",
          joinColumns = @JoinColumn(name = "activity_id"),
          inverseJoinColumns = @JoinColumn(name = "participant_id"))
  private Set<Participant> participants = new HashSet<>();

  public Activity() {
  }

  public Activity(String name, String description, double price, Category category) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.category = category;
  }

  @OneToMany(mappedBy = "activity")
  private Set<Block> blocks = new HashSet<>();

  public Set<Participant> getParticipants() {
    return participants;
  }

  public Set<Block> getBlocks() {
    return blocks;
  }


}
