package com.example.demo.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.TaxEntity;
import com.example.demo.Repo.TaxRepo;

@Service
public class TaxServiceimpl implements TaxService {
	
	@Autowired
	TaxRepo taxRepo;

	@Override
	public TaxEntity saveTaxDetails(TaxEntity taxEntity) {
		
		return taxRepo.save(taxEntity);
	}

	@Override
	public List<TaxEntity> getAllTaxDetails() {
		
		return taxRepo.findAll();
	}

	@Override
	public List<TaxEntity> calculateTaxDetails() {
		
		return taxRepo.findAll().stream()
                .map(this::calculateTaxForEmployee)
                .collect(Collectors.toList());
	}
	
	//calculate for each person
	private TaxEntity calculateTaxForEmployee(TaxEntity taxEntity) {
        LocalDate doj = taxEntity.getDateOfJoining();
        LocalDate financialYearStart = LocalDate.of(LocalDate.now().getYear(), Month.APRIL, 1);
        if (doj.isAfter(financialYearStart)) {
            financialYearStart = doj;
        }
        LocalDate financialYearEnd = financialYearStart.plusYears(1).minusDays(1);

        LocalDate start = doj.isAfter(financialYearStart) ? doj : financialYearStart;
        LocalDate end = financialYearEnd.isBefore(LocalDate.now()) ? financialYearEnd : LocalDate.now();

       
        long daysWorked = ChronoUnit.DAYS.between(start, end) + 1;
        double monthlySalary = taxEntity.getSalary();
        double dailySalary = monthlySalary / 30;
        double totalSalary = (daysWorked / 30.0) * monthlySalary;

        double tax = calculateTaxAmount(totalSalary);
        double cess = totalSalary > 2500000 ? (totalSalary - 2500000) * 0.02 : 0;

        System.out.printf("Employee ID: %d, Name: %s %s, Yearly Salary: %.2f, Tax: %.2f, Cess: %.2f%n",
                taxEntity.getEmployeeId(), taxEntity.getFirstName(), taxEntity.getLastName(), totalSalary, tax, cess);

        return taxEntity;
    }

    private double calculateTaxAmount(double totalSalary) {
        if (totalSalary <= 250000) {
            return 0;
        } else if (totalSalary <= 500000) {
            return (totalSalary - 250000) * 0.05;
        } else if (totalSalary <= 1000000) {
            return 250000 * 0.05 + (totalSalary - 500000) * 0.10;
        } else {
            return 250000 * 0.05 + 500000 * 0.10 + (totalSalary - 1000000) * 0.20;
        }
    }
}
      
