package com.fastcampus.web_ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;

}
