package com.petadoption.dto;

import com.petadoption.entity.Pet;
import java.time.LocalDateTime;

public class PetDTO {

    private Integer petId;
    private Integer ownerId;
    private String ownerName;
    private String petName;
    private String category;
    private String breed;
    private int age;
    private String gender;
    private String description;
    private String imageUrl;
    private String status;
    private LocalDateTime createdAt;

    public PetDTO() {}

    public static PetDTO fromEntity(Pet p) {
        PetDTO dto = new PetDTO();
        dto.petId = p.getId();
        dto.ownerId = p.getOwner() != null ? p.getOwner().getId() : null;
        dto.ownerName = p.getOwner() != null ? p.getOwner().getFullName() : null;
        dto.petName = p.getPetName();
        dto.category = p.getCategory();
        dto.breed = p.getBreed();
        dto.age = p.getAge();
        dto.gender = p.getGender();
        dto.description = p.getDescription();
        dto.imageUrl = p.getImageUrl();
        dto.status = p.getStatus();
        dto.createdAt = p.getCreatedAt();
        return dto;
    }

    public Integer getPetId() { return petId; }
    public void setPetId(Integer petId) { this.petId = petId; }

    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
