package com.petadoption.dto;

import com.petadoption.entity.User;
import java.time.LocalDateTime;

public class UserDTO {

    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime createdAt;

    public UserDTO() {}

    public static UserDTO fromEntity(User u) {
        UserDTO dto = new UserDTO();
        dto.userId = u.getId();
        dto.fullName = u.getFullName();
        dto.email = u.getEmail();
        dto.phone = u.getPhone();
        dto.role = u.getRole();
        dto.createdAt = u.getCreatedAt();
        return dto;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
