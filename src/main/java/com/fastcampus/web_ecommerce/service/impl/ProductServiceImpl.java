package com.fastcampus.web_ecommerce.service.impl;

import com.fastcampus.web_ecommerce.entity.Category;
import com.fastcampus.web_ecommerce.entity.Product;
import com.fastcampus.web_ecommerce.entity.ProductCategory;
import com.fastcampus.web_ecommerce.exception.custom.NotFoundException;
import com.fastcampus.web_ecommerce.mapper.ProductMapper;
import com.fastcampus.web_ecommerce.repository.CategoryRepository;
import com.fastcampus.web_ecommerce.repository.ProductCategoryRepository;
import com.fastcampus.web_ecommerce.repository.ProductRepository;
import com.fastcampus.web_ecommerce.request.CreateProductRequest;
import com.fastcampus.web_ecommerce.request.UpdateProductRequest;
import com.fastcampus.web_ecommerce.response.product.PaginationGetProductResponse;
import com.fastcampus.web_ecommerce.response.catogory.GetCategoryResponse;
import com.fastcampus.web_ecommerce.response.product.CreateProductResponse;
import com.fastcampus.web_ecommerce.response.product.GetProductResponse;
import com.fastcampus.web_ecommerce.response.product.UpdateProductResponse;
import com.fastcampus.web_ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<GetProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(product -> {
                    List<GetCategoryResponse> categoryResponses =
                            getProductCategoryByProductId(product.getProductId());
                    return GetProductResponse.fromProductAndCategorys(product, categoryResponses);
                })
                .toList();
    }

    @Override
    public Page<GetProductResponse> findByPage(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(product -> {
                    List<GetCategoryResponse> categoryResponses =
                            getProductCategoryByProductId(product.getProductId());
                    return GetProductResponse.fromProductAndCategorys(product, categoryResponses);
                });
    }

    @Override
    public GetProductResponse findById(Long productId) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        List<GetCategoryResponse> categoryResponses =
                getProductCategoryByProductId(productId);

        return GetProductResponse.fromProductAndCategorys(existingProduct, categoryResponses);

    }

    @Override
    @Transactional
    public CreateProductResponse create(CreateProductRequest request) {

        List<Category> categories = getCategorysByIds(request.getCategoryIds());

        Product product = ProductMapper.INSTANCE.mapToProduct(request);
        Product createdProduct = productRepository.save(product);

        List<ProductCategory> productCategories = getProductCategories(categories, createdProduct.getProductId());
        productCategoryRepository.saveAll(productCategories);

        return ProductMapper.INSTANCE.mapToCreateProductResponse(createdProduct);

    }

    @Override
    public UpdateProductResponse update(UpdateProductRequest request) {

        productRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + request.getId()));

        List<Category> categories = getCategorysByIds(request.getCategoryIds());

        Product existingProduct = ProductMapper.INSTANCE.mapToProduct(request);
        productRepository.save(existingProduct);

        List<ProductCategory> existingProductCategory =
                productCategoryRepository.findByProductId(request.getId());
        productCategoryRepository.deleteAll(existingProductCategory);
        List<ProductCategory> productCategories = getProductCategories(categories, existingProduct.getProductId());
        productCategoryRepository.saveAll(productCategories);

        return ProductMapper.INSTANCE.mapToUpdateProductResponse(existingProduct);

    }

    @Override
    @Transactional
    public void deleteById(Long productId) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        List<ProductCategory> productCategoryList = productCategoryRepository.findByProductId(productId);

        productCategoryRepository.deleteAll(productCategoryList);
        productRepository.deleteById(productId);

    }

    @Override
    public PaginationGetProductResponse convertToPage(Page<GetProductResponse> request) {
        return PaginationGetProductResponse.builder()
                .datas(request.getContent())
                .pageNo(request.getNumber())
                .pageSize(request.getSize())
                .totalElements(request.getTotalElements())
                .totalPages(request.getTotalPages())
                .last(request.isLast())
                .build();
    }

    private List<Category> getCategorysByIds(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new NotFoundException("Category not found")))
                .toList();
    }

    private List<ProductCategory> getProductCategories(List<Category> categories, Long productId) {
        return categories.stream()
                .map(category -> {
                    ProductCategory productCategory = ProductCategory.builder().build();
                    ProductCategory.ProductCategoryId productCategoryId = new ProductCategory.ProductCategoryId();
                    productCategoryId.setProductId(productId);
                    productCategoryId.setCategoryId(category.getCategoryId());
                    productCategory.setId(productCategoryId);
                    return productCategory;
                }).toList();
    }

    private List<GetCategoryResponse> getProductCategoryByProductId(Long productId) {

        List<ProductCategory> productCategories = productCategoryRepository.findByProductId(productId);
        List<Long> categoryIds = productCategories.stream()
                .map(productCategory -> productCategory.getId().getCategoryId())
                .toList();

        return categoryRepository.findAllById(categoryIds).stream()
                .map(GetCategoryResponse::fromCategory)
                .toList();

    }
}
