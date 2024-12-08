package com.fastcampus.web_ecommerce.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotNull(message = "Name tidak boleh kosong")
    private String name;

    @Positive(message = "Harga harus lebih besar dari 0")
    private BigDecimal price;

    private String description;

}
