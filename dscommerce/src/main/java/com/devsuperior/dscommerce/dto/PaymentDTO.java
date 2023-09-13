package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor @NoArgsConstructor @Data
public class PaymentDTO {


  private Long id;
  private Instant moment;

  public PaymentDTO(Payment payment) {
    this.id = payment.getId();
    this.moment = payment.getMoment();
  }

}
