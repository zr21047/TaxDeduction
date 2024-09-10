package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.TaxEntity;

public interface TaxService {
	
	TaxEntity saveTaxDetails(TaxEntity taxEntity);
	
	List<TaxEntity> getAllTaxDetails();
	
	List<TaxEntity> calculateTaxDetails();

}
