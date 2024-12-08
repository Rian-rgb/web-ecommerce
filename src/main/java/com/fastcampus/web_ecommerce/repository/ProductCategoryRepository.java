package com.fastcampus.web_ecommerce.repository;

import com.fastcampus.web_ecommerce.entity.Category;
import com.fastcampus.web_ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategory.ProductCategoryId> {
}
