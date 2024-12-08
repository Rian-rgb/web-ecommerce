package com.fastcampus.web_ecommerce.repository;

import com.fastcampus.web_ecommerce.entity.Category;
import com.fastcampus.web_ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
