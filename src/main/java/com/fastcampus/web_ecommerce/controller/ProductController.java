package com.fastcampus.web_ecommerce.controller;

import com.fastcampus.web_ecommerce.request.CreateProductRequest;
import com.fastcampus.web_ecommerce.request.UpdateProductRequest;
import com.fastcampus.web_ecommerce.response.product.PaginationGetProductResponse;
import com.fastcampus.web_ecommerce.response.product.CreateProductResponse;
import com.fastcampus.web_ecommerce.response.product.GetProductResponse;
import com.fastcampus.web_ecommerce.response.product.UpdateProductResponse;
import com.fastcampus.web_ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponse> getById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<PaginationGetProductResponse> getAllProductByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productId,asc") String[] sort
    ) {

        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<GetProductResponse> result = productService.findByPage(pageable);
        return ResponseEntity.ok(productService.convertToPage(result));

    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @PutMapping
    public ResponseEntity<UpdateProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetProductResponse> deleteById(@PathVariable("id") Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    private Sort.Direction getSortDirection(String direction) {

        if (direction.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;

    }
}
