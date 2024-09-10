package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.TaxEntity;

public interface TaxRepo extends JpaRepository<TaxEntity, Long>{

}
