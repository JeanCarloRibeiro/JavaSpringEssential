package com.devsuperior.crud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ClientDTO {

  private Long id;
  @NotBlank
  @Size(min = 3, max = 100)
  private String name;
  @NotBlank
  @Size(min = 11, max = 11)
  private String cpf;
  @Positive(message = "Deve ser maior que 0")
  private double income;
  @PastOrPresent(message = "Não pode ser maior que a data atual")
  private LocalDate birthDate;
  @Min(value = 0, message = "Deve ser pelo menos 0")
  private Integer children;

  public ClientDTO() {
  }

  public ClientDTO(String name, String cpf, double income, LocalDate birthDate, Integer children) {
    this.name = name;
    this.cpf = cpf;
    this.income = income;
    this.birthDate = birthDate;
    this.children = children;
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

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public double getIncome() {
    return income;
  }

  public void setIncome(double income) {
    this.income = income;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Integer getChildren() {
    return children;
  }

  public void setChildren(Integer children) {
    this.children = children;
  }
}
