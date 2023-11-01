package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Seller;
import com.example.demo.service.SellerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/all")
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @GetMapping("/{id}")
    public Seller getSeller(@PathVariable Long id) {
        Optional<Seller> seller = sellerService.getSellerById(id);

        if (seller.isPresent()) {
            return seller.get();
        } else {
            throw new RuntimeException("Seller not found for id: " + id);
        }
    }

    @PostMapping("/add")
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    @PutMapping("/update/{id}")
    public Seller updateSeller(@PathVariable Long id, @RequestBody Seller updatedSeller) {
        return sellerService.updateSeller(id, updatedSeller);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
    }
}
