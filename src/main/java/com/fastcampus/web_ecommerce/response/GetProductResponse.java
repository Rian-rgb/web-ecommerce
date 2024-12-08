package com.fastcampus.web_ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponse {

    private String name;
    private BigDecimal price;
    private String description;

}
