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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
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

  @OneToMany(mappedBy = "activity")
  private Set<Block> blocks = new HashSet<>();

}
