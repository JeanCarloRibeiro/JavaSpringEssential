import model.Employee;
import services.BrazilTaxService;
import services.PensionService;
import services.SalaryService;
import services.TaxService;

import java.util.Locale;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");

    Locale.setDefault(Locale.US);
    Scanner sc = new Scanner(System.in);

    System.out.println("Nome: ");
    String name = sc.nextLine();

    System.out.println("Salario: ");
    double grossSalary = sc.nextDouble();

    Employee employee = new Employee(name, grossSalary);
    TaxService taxService = new BrazilTaxService();
    PensionService pensionService = new PensionService();

    SalaryService salaryService = new SalaryService(taxService, pensionService);
    double netSalary = salaryService.netSalary(employee);

    System.out.printf("Salario liquido = %.2f%n", netSalary);



    sc.close();

  }


}