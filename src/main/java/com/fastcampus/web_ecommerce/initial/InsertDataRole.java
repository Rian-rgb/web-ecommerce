package com.fastcampus.web_ecommerce.initial;

import com.fastcampus.web_ecommerce.entity.Role;
import com.fastcampus.web_ecommerce.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InsertDataRole {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void insertDataRole() {

        List<Role> existingRoles = roleRepository.findAll();
        if (existingRoles.isEmpty()) {

            List<Role> roles = new ArrayList<>();

            Role data1 = new Role();
            data1.setName("ROLE_ADMIN");
            data1.setDescription("Administrative user role");

            Role data2 = new Role();
            data2.setName("ROLE_USER");
            data2.setDescription("Standard user role");

            roles.add(data1);
            roles.add(data2);

            roleRepository.saveAll(roles);

        }
    }
}
