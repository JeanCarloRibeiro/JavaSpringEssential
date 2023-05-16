package services;

import model.Employee;

public class SalaryService {

  private TaxService taxService;
  private PensionService pensionService;

  public SalaryService(TaxService taxService, PensionService pensionService) {
    this.taxService = taxService;
    this.pensionService = pensionService;
  }

  public double netSalary(Employee employee) {
    double netSalary = employee.getGrossSalary();
    return netSalary - this.taxService.tax(netSalary) - this.pensionService.discount(netSalary);
  }

}
