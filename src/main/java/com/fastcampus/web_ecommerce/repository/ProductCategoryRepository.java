package com.fastcampus.web_ecommerce.repository;

import com.fastcampus.web_ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategory.ProductCategoryId> {

    @Query(value = """
            SELECT product_id,
            category_id,
            created_at,
            updated_at FROM product_category where product_id =:id
            """, nativeQuery = true)
    List<ProductCategory> findByProductId(Long id);
}
