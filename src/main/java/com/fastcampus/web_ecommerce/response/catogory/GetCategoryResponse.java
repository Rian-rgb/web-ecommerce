package com.fastcampus.web_ecommerce.response.catogory;

import com.fastcampus.web_ecommerce.entity.Category;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetCategoryResponse {

    private Long categoryId;
    private String name;

    public static GetCategoryResponse fromCategory(Category category) {
        return GetCategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
    }

}
