package com.fastcampus.web_ecommerce.mapper;

import com.fastcampus.web_ecommerce.entity.Product;
import com.fastcampus.web_ecommerce.request.CreateProductRequest;
import com.fastcampus.web_ecommerce.request.UpdateProductRequest;
import com.fastcampus.web_ecommerce.response.product.CreateProductResponse;
import com.fastcampus.web_ecommerce.response.product.UpdateProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product mapToProduct(CreateProductRequest request);

    @Mapping(source = "id", target = "productId")
    Product mapToProduct(UpdateProductRequest request);

    CreateProductResponse mapToCreateProductResponse(Product product);

    UpdateProductResponse mapToUpdateProductResponse(Product product);
}
