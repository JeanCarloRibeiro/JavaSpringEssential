package com.devsuperior.services;

import com.devsuperior.model.Order;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

  public double shipment(Order order) {
    double basic = order.getBasic();
    if (basic < 100) {
      basic = 20;
    } else if (basic >= 100 && basic <=200) {
      basic = 12;
    } else {
      basic = 0;
    }
    return basic;
  }

}
