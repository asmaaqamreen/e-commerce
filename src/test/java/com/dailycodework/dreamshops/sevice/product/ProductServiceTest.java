package com.dailycodework.dreamshops.sevice.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.service.product.ProductService;

// --- Domain & exception imports as per your package structure ---
// import com.example.product.domain.Product;
// import com.example.product.domain.Category;
// import com.example.product.exception.ResourceNotFoundException;
// import com.example.product.repository.ProductRepository;
// import com.example.product.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("getProductById returns domain Product when found")
    void getProductById_returnsProduct_whenFound() {
        // Arrange
        Long productId = 42L;

        Category category = new Category();
        category.setId(10L);
        category.setName("Electronics");

        Product product = new Product();
        product.setId(42L);
        product.setName("Laptop");
        product.setBrand("Dream");
        product.setPrice(new BigDecimal("1999.00"));
        product.setInventory(7);
        product.setDescription("desc");
        product.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(42L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals("Dream", result.getBrand());
        assertEquals(new BigDecimal("1999.00"), result.getPrice());
        assertEquals(7, result.getInventory());
        assertEquals("desc", result.getDescription());
        assertEquals(10L, result.getCategory().getId());
        assertEquals("Electronics", result.getCategory().getName());

        // Verify interactions and exact id passed
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(productRepository, times(1)).findById(idCaptor.capture());
        assertEquals(productId, idCaptor.getValue());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("getProductById throws ResourceNotFoundException when product is missing")
    void getProductById_throwsResourceNotFoundException_whenMissing() {
        // Arrange
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(productId));

        assertTrue(ex.getMessage().contains("Product not found"));
        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productRepository);
    }

  
}
