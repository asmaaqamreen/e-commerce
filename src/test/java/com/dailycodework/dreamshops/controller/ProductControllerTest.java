////
//    package com.dailycodework.dreamshops.controller;
//    
//    import com.dailycodework.dreamshops.dto.ImageDto;
//    import com.dailycodework.dreamshops.dto.ProductDto;
//import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
//import com.dailycodework.dreamshops.model.Category;
//    import com.dailycodework.dreamshops.model.Product;
//import com.dailycodework.dreamshops.request.AddProductRequest;
//import com.dailycodework.dreamshops.response.ApiResponse;
//    import com.dailycodework.dreamshops.service.product.ProductService;
//     
//    import com.fasterxml.jackson.core.type.TypeReference;
//    import com.fasterxml.jackson.databind.ObjectMapper;
//     
//    import org.junit.jupiter.api.DisplayName;
//    import org.junit.jupiter.api.Test;
//    import org.mockito.ArgumentMatchers;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//    import org.springframework.boot.test.mock.mockito.MockBean;
//    import org.springframework.http.MediaType;
//    import org.springframework.test.web.servlet.MockMvc;
//     
//    import java.math.BigDecimal;
//    import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//     
//
//    @WebMvcTest(ProductController.class)
//    class ProductControllerTest {
//     
//        @Autowired
//        private MockMvc mockMvc;
//     
//        @Autowired
//        private ObjectMapper objectMapper; 
//     
//        @MockBean
//        private ProductService productService;
//     
//        @Test
//        @DisplayName("GET /api/v1/products/all returns converted ProductDto list wrapped in ApiResponse")
//        void getAllProducts_returnsConvertedDtos() throws Exception {
//        	
//            Product product = new Product();
//            product.setId(1L);
//            product.setName("Phone");
//            product.setBrand("Apple");
//            product.setPrice(new BigDecimal("999.99"));
//            product.setInventory(5);
//            product.setDescription("Apple Electronics");
//     
//
//            Category electronics = new Category();
//            electronics.setId(10L);
//            electronics.setName("Electronics");
//     
//            ImageDto imageDto = new ImageDto(100L,"Applephone.png", "https://cdn.example.com/img/phone.png");
//     
//            ProductDto productDto = new ProductDto(
//                    1L,
//                    "Phone",
//                    "Apple",
//                    new BigDecimal("999.99"),
//                    5,                      
//                    "Apple Electronics",               
//                    electronics,
//                    List.of(imageDto)
//            );
//     
//            //  Mocking service interactions 
//            when(productService.getAllProducts()).thenReturn(List.of(product));
//            when(productService.getConvertedProducts(ArgumentMatchers.anyList()))
//                    .thenReturn(List.of(productDto));
//     
//            //  Call the endpoint 
//            String json = mockMvc.perform(get("/api/v1/products/all")
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                    .andReturn()
//                    .getResponse()
//                    .getContentAsString();
//     
//
//            ApiResponse<List<ProductDto>> response = objectMapper.readValue(
//                json,
//                new com.fasterxml.jackson.core.type.TypeReference<ApiResponse<List<ProductDto>>>() {}
//            );
//
//            assertEquals("success", response.getMessage());
//
//            List<ProductDto> list = response.getData();
//
//            ProductDto dto = list.get(0);
//
//
//            assertAll(
//                    () -> assertEquals(1L, dto.getId()),
//                    () -> assertEquals("Phone", dto.getName()),
//                    () -> assertEquals(new BigDecimal("999.99"), dto.getPrice()),
//                    () -> assertEquals("Apple", dto.getBrand()),
//                    () -> assertEquals(5, dto.getInventory()),
//                    () -> assertEquals("Apple Electronics", dto.getDescription()),
//                    () -> assertEquals(10L, dto.getCategory().getId()),
//                    () -> assertEquals("Electronics", dto.getCategory().getName()),
//                    () -> assertEquals(1, dto.getImages().size()),
//                    () -> assertEquals(100L, dto.getImages().get(0).getId()),
//                    () -> assertEquals("Applephone.png", dto.getImages().get(0).getFileName()),
//                    () -> assertEquals("https://cdn.example.com/img/phone.png", dto.getImages().get(0).getDownloadUrl())
//            );
//        }
//    
// 
//@Test
//@DisplayName("GET /api/v1/products/product/{productId}/product returns ProductDto wrapped in ApiResponse")
//void getProductById_returnsConvertedDto() throws Exception {
//
//    // Arrange: Domain model
//    Product product = new Product();
//    product.setId(42L);
//    product.setName("Laptop");
//    product.setBrand("Dream");
//    product.setPrice(new BigDecimal("1999.00"));
//    product.setInventory(7);
//    product.setDescription("desc");
//
//    Category category = new Category();
//    category.setId(10L);
//    category.setName("Electronics");
//    product.setCategory(category);
//
//    ImageDto imageDto = new ImageDto(100L, "laptop.png", "https://cdn.example.com/img/laptop.png");
//
//    ProductDto productDto = new ProductDto(
//            42L,
//            "Laptop",
//            "Dream",
//            new BigDecimal("1999.00"),
//            7,
//            "desc",
//            category,
//            List.of(imageDto)
//    );
//
//    // Mock service
//    when(productService.getProductById(42L)).thenReturn(product);
//    when(productService.convertToDto(product)).thenReturn(productDto);
//
//    // Act: Perform request
//    String json = mockMvc.perform(get("/api/v1/products/product/{productId}/product", 42L)
//                    .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andReturn()
//            .getResponse()
//            .getContentAsString();
//
//    ApiResponse<ProductDto> response = objectMapper.readValue(
//            json,
//            new com.fasterxml.jackson.core.type.TypeReference<ApiResponse<ProductDto>>() {}
//        );
//
//        assertEquals("success", response.getMessage());
//
//        ProductDto dto = response.getData();
//
//      
//        
//        
//    assertAll(
//            () -> assertEquals(42L, dto.getId()),
//            () -> assertEquals("Laptop", dto.getName()),
//            () -> assertEquals(new BigDecimal("1999.00"), dto.getPrice()),
//            () -> assertEquals("Dream", dto.getBrand()),
//            () -> assertEquals(7, dto.getInventory()),
//            () -> assertEquals("desc", dto.getDescription()),
//            () -> assertEquals(10L, dto.getCategory().getId()),
//            () -> assertEquals("Electronics", dto.getCategory().getName()),
//            () -> assertEquals(1, dto.getImages().size()),
//            () -> assertEquals(100L, dto.getImages().get(0).getId()),
//            () -> assertEquals("laptop.png", dto.getImages().get(0).getFileName()),
//            () -> assertEquals("https://cdn.example.com/img/laptop.png", dto.getImages().get(0).getDownloadUrl())
//    );
//}
//
//
//@Test
//@DisplayName("GET /api/v1/products/product/{productId}/product returns 404 with ApiResponse error message when product is missing")
//void getProductById_returns404WhenMissing() throws Exception {
//
//	when(productService.getProductById(99L))
//            .thenThrow(new ResourceNotFoundException("Product not found"));
//
//    String json = mockMvc.perform(get("/api/v1/products/product/{productId}/product", 99L)
//                    .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andReturn()    
//            .getResponse()
//            .getContentAsString();
//
//    ApiResponse response = objectMapper.readValue(json, new TypeReference<ApiResponse>() {});
//    assertAll(
//            () -> assertEquals("Product not found", response.getMessage()),
//            () -> assertNull(response.getData(), "Expected data to be null on error responses")
//            );
//}
//
// 
//
//@Test
//@DisplayName("POST /api/v1/products/add returns created ProductDto wrapped in ApiResponse")
//void addProduct_returns200OnSuccess() throws Exception {
//	
//    Category audio = new Category();
//    audio.setId(1L);
//    audio.setName("Audio");
//
//    ImageDto imageDto = new ImageDto(
//            200L,
//            "headphones.png",
//            "https://cdn.example.com/img/headphones.png"
//    );
//
//    AddProductRequest req = new AddProductRequest(
//            "Headphones",
//            "Dream",
//            new BigDecimal("199.00"),    
//            10,
//            "desc",
//            audio
//    );
//
//    Product created = new Product();
//    created.setId(10L);
//    created.setName("Headphones");
//    created.setBrand("Dream");
//    created.setPrice(new BigDecimal("199.00"));
//    created.setInventory(10);
//    created.setDescription("desc");
//    created.setCategory(audio);
//
//    ProductDto createdDto = new ProductDto(
//            10L,
//            "Headphones",
//            "Dream",
//            new BigDecimal("199.00"),
//            10,
//            "desc",
//            audio,
//            List.of(imageDto)
//    );
//
//    when(productService.addProduct(any(AddProductRequest.class))).thenReturn(created);
//    when(productService.convertToDto(created)).thenReturn(createdDto);
//
//    String json = mockMvc.perform(post("/api/v1/products/add")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(req))
//                    .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andReturn()
//            .getResponse()
//            .getContentAsString();
//
//    ApiResponse<ProductDto> response = objectMapper.readValue(
//            json,
//            new com.fasterxml.jackson.core.type.TypeReference<ApiResponse<ProductDto>>() {}
//        );
//
//        assertEquals("Add product success!", response.getMessage());
//
//        ProductDto dto = response.getData();
//        
//    assertAll(
//            () -> assertEquals(10L, dto.getId()),
//            () -> assertEquals("Headphones", dto.getName()),
//            () -> assertEquals("Dream", dto.getBrand()),
//            () -> assertEquals(new BigDecimal("199.00"), dto.getPrice()),  // BigDecimal comparison
//            () -> assertEquals(10, dto.getInventory()),
//            () -> assertEquals("desc", dto.getDescription()),
//            () -> assertNotNull(dto.getCategory()),
//            () -> assertEquals(1L, dto.getCategory().getId()),
//            () -> assertEquals("Audio", dto.getCategory().getName()),
//            () -> assertNotNull(dto.getImages()),
//            () -> assertEquals(1, dto.getImages().size()),
//            () -> assertEquals(200L, dto.getImages().get(0).getId()),
//            () -> assertEquals("headphones.png", dto.getImages().get(0).getFileName()),
//            () -> assertEquals("https://cdn.example.com/img/headphones.png", dto.getImages().get(0).getDownloadUrl())
//    );
//}
//
//
//    }
//   