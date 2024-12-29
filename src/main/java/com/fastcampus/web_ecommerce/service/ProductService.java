package com.fastcampus.web_ecommerce.service;

import com.fastcampus.web_ecommerce.request.product.CreateProductRequest;
import com.fastcampus.web_ecommerce.request.product.UpdateProductRequest;
import com.fastcampus.web_ecommerce.response.product.PaginationGetProductResponse;
import com.fastcampus.web_ecommerce.response.product.CreateProductResponse;
import com.fastcampus.web_ecommerce.response.product.GetProductResponse;
import com.fastcampus.web_ecommerce.response.product.UpdateProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<GetProductResponse> findAll();

    Page<GetProductResponse> findByPage(Pageable pageable);

    GetProductResponse findById(Long id);

    CreateProductResponse create(CreateProductRequest request);

    UpdateProductResponse update(UpdateProductRequest request);

    void deleteById(Long id);

    PaginationGetProductResponse convertToPage(Page<GetProductResponse> request);
}
