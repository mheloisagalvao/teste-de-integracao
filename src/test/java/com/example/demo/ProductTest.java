package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class ProductTest {

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Jeans");
        product.setPrice(49.99);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductService productService = new ProductService(productRepository);

        Optional<Product> retrievedProduct = productService.getProductById(1L);

        assertEquals("Jeans", retrievedProduct.get().getName());
        assertEquals(49.99, retrievedProduct.get().getPrice(), 0.01);
    }

    @Test
    public void testGetAllProducts() {

        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Jeans");
        product1.setPrice(49.99);
        productList.add(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("T-shirt");
        product2.setPrice(19.99);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList);

        ProductService productService = new ProductService(productRepository);

        List<Product> retrievedProducts = productService.getAllProducts();

        assertEquals(2, retrievedProducts.size());
        assertEquals("Jeans", retrievedProducts.get(0).getName());
        assertEquals("T-shirt", retrievedProducts.get(1).getName());
    }

    @Test
    public void testCreateProduct() {

        Product product = new Product();
        product.setName("Sneakers");
        product.setPrice(69.99);

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ProductService productService = new ProductService(productRepository);

        Product createdProduct = productService.createProduct(product);

        assertEquals("Sneakers", createdProduct.getName());
        assertEquals(69.99, createdProduct.getPrice(), 0.01);
    }

    @Test
    public void testUpdateProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Hoodie");
        product.setPrice(34.99);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ProductService productService = new ProductService(productRepository);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Jacket");
        updatedProduct.setPrice(54.99);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertEquals("Jacket", result.getName());
        assertEquals(54.99, result.getPrice(), 0.01);
    }

    @Test
    public void testUpdateProduct_NotFound() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductService productService = new ProductService(productRepository);

        assertThrows(RuntimeException.class, () -> productService.updateProduct(1L, new Product()));
    }
}