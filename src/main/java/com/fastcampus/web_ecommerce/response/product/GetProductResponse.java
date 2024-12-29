package com.fastcampus.web_ecommerce.response.product;

import com.fastcampus.web_ecommerce.entity.Category;
import com.fastcampus.web_ecommerce.entity.Product;
import com.fastcampus.web_ecommerce.response.catogory.GetCategoryResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetProductResponse {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private BigDecimal weight;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    private List<GetCategoryResponse> categorys;

    public static GetProductResponse fromProductAndCategorys(Product product, List<GetCategoryResponse> categorys) {
        return GetProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .weight(product.getWeight())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .categorys(categorys)
                .build();
    }
}
