package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
