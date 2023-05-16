package com.devsuperior.services;

import org.springframework.stereotype.Service;

@Service
public class BrazilTaxService extends TaxService {

  @Override
  public double tax(double amount) {
    return amount*0.3;
  }

}
