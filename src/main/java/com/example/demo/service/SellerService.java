package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Seller;
import com.example.demo.repository.SellerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller updateSeller(Long id, Seller updatedSeller) {
        Optional<Seller> existingSeller = sellerRepository.findById(id);

        if (existingSeller.isPresent()) {
            Seller seller = existingSeller.get();
            seller.setName(updatedSeller.getName());
            seller.setEmail(updatedSeller.getEmail());
            return sellerRepository.save(seller);
        } else {
            throw new RuntimeException("Seller not found for id: " + id);
        }
    }

    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }
}
