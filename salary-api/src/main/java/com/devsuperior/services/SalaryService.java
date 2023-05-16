package com.devsuperior.services;

import com.devsuperior.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

  @Autowired
  private TaxService taxService;
  @Autowired
  private PensionService pensionService;

  public double netSalary(Employee employee) {
    double netSalary = employee.getGrossSalary();
    return netSalary - this.taxService.tax(netSalary) - this.pensionService.discount(netSalary);
  }

}
