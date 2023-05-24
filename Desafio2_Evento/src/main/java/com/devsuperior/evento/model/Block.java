package com.devsuperior.evento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "tb_block")
public class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private Instant inicio;
  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private Instant fim;
  @Setter(AccessLevel.NONE)
  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  public Block(Instant inicio, Instant fim, Activity activity) {
    this.inicio = inicio;
    this.fim = fim;
    this.activity = activity;
  }

  public Activity getActivity() {
    return activity;
  }




}
