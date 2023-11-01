package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Seller;
import com.example.demo.repository.SellerRepository;
import com.example.demo.service.SellerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class SellerTest {

    @Mock
    private SellerRepository sellerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSellerById() {
        
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Alice");

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        SellerService sellerService = new SellerService(sellerRepository);

        Optional<Seller> retrievedSeller = sellerService.getSellerById(1L);

        assertEquals("Alice", retrievedSeller.get().getName());
    }

    @Test
    public void testGetAllSellers() {
        
        List<Seller> sellerList = new ArrayList<>();
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Alice");
        sellerList.add(seller1);

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("Bob");
        sellerList.add(seller2);

        when(sellerRepository.findAll()).thenReturn(sellerList);

        SellerService sellerService = new SellerService(sellerRepository);

        List<Seller> retrievedSellers = sellerService.getAllSellers();

        assertEquals(2, retrievedSellers.size());
        assertEquals("Alice", retrievedSellers.get(0).getName());
        assertEquals("Bob", retrievedSellers.get(1).getName());
    }

    @Test
    public void testCreateSeller() {

        Seller seller = new Seller();
        seller.setName("Charlie");

        when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);

        SellerService sellerService = new SellerService(sellerRepository);

        Seller createdSeller = sellerService.createSeller(seller);

        assertEquals("Charlie", createdSeller.getName());
    }

    @Test
    public void testUpdateSeller() {

        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("David");

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);

        SellerService sellerService = new SellerService(sellerRepository);

        Seller updatedSeller = new Seller();
        updatedSeller.setId(1L);
        updatedSeller.setName("Eve");

        Seller result = sellerService.updateSeller(1L, updatedSeller);

        assertEquals("Eve", result.getName());
    }

    @Test
    public void testUpdateSeller_NotFound() {

        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        SellerService sellerService = new SellerService(sellerRepository);

        assertThrows(RuntimeException.class, () -> sellerService.updateSeller(1L, new Seller()));
    }
}