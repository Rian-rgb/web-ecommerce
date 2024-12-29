package com.fastcampus.web_ecommerce.repository;

import com.fastcampus.web_ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT E FROM User E
            WHERE username =:keyword OR
            email =:keyword
            """)
    Optional<User> findByKeyword(String keyword);

    boolean existsByusername(String username);

    boolean existsByEmail(String email);
}
