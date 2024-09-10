package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.TaxEntity;
import com.example.demo.Service.TaxService;

@RestController
public class TaxController {
	
	@Autowired
	private TaxService taxService;
	
    @PostMapping("/add")
    public ResponseEntity<TaxEntity> addEmployee(@RequestBody TaxEntity taxEntity) {
        TaxEntity savedEmployee = taxService.saveTaxDetails(taxEntity);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/getDetails")
    public ResponseEntity<List<TaxEntity>> getAllEmployees() {
        List<TaxEntity> employees = taxService.getAllTaxDetails();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/taxCalculation")
    public ResponseEntity<List<TaxEntity>> calculateTax() {
        List<TaxEntity> taxDetails = taxService.calculateTaxDetails();
        return ResponseEntity.ok(taxDetails);
    }
}
