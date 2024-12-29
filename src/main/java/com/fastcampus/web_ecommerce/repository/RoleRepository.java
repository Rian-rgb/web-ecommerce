package com.fastcampus.web_ecommerce.repository;

import com.fastcampus.web_ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = """
            select s.role_id,
            s.name,
            s.description,
            s.created_at,
            s.updated_at from roles s
            join user_role ur on s.role_id = ur.role_id
            join users u on ur.user_id = u.user_id
            where u.user_id =:userId
            """, nativeQuery = true)
    List<Role> findByUserId(Long userId);

    Optional<Role> findByName(String roleAdmin);
}
