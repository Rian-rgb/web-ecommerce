package com.fastcampus.web_ecommerce.response.user;

import com.fastcampus.web_ecommerce.entity.Role;
import com.fastcampus.web_ecommerce.entity.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateUserResponse implements Serializable {

   private Long userId;
   private String username;
   private String email;
   private boolean enabled;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private List<String> roles;

   public static UpdateUserResponse fromUserAndRoles(User user, List<Role> roles) {
       return UpdateUserResponse.builder()
               .userId(user.getUserId())
               .username(user.getUsername())
               .email(user.getEmail())
               .enabled(user.isEnabled())
               .createdAt(user.getCreatedAt())
               .updatedAt(user.getUpdatedAt())
               .roles(roles.stream().map(Role::getName).toList())
               .build();
   }

}
