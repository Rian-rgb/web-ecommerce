package com.fastcampus.web_ecommerce.controller;

import com.fastcampus.web_ecommerce.request.CreateProductRequest;
import com.fastcampus.web_ecommerce.request.UpdateProductRequest;
import com.fastcampus.web_ecommerce.response.CreateProductResponse;
import com.fastcampus.web_ecommerce.response.GetProductResponse;
import com.fastcampus.web_ecommerce.response.UpdateProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponse> getById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(
                GetProductResponse.builder()
                .name("Product-%s".formatted(productId))
                .price(BigDecimal.ONE)
                .description("deskripsi product")
                .build());
    }

    @GetMapping
    public ResponseEntity<List<GetProductResponse>> getAllProducct() {
        return ResponseEntity.ok(
                List.of(
                        GetProductResponse.builder()
                        .name("Product-1")
                        .price(BigDecimal.ONE)
                        .description("deskripsi product")
                        .build(),
                        GetProductResponse.builder()
                                .name("Product-2")
                                .price(BigDecimal.ONE)
                                .description("deskripsi product 2")
                                .build()
                        )
          );
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.ok(
                CreateProductResponse.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build());
    }

    @PutMapping
    public ResponseEntity<UpdateProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(
                UpdateProductResponse.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build());
    }
}
