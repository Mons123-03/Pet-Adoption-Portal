package com.petadoption.dto;

import com.petadoption.entity.AdoptionRequest;
import java.time.LocalDateTime;

public class AdoptionRequestDTO {

    private Integer requestId;
    private Integer petId;
    private Integer adopterId;
    private String message;
    private String status;
    private LocalDateTime requestedAt;

    // Extra display fields pulled in via the related Pet/User
    private String petName;
    private String imageUrl;
    private String category;
    private String adopterName;
    private String adopterEmail;
    private String adopterPhone;

    public AdoptionRequestDTO() {}

    public static AdoptionRequestDTO fromEntity(AdoptionRequest r) {
        AdoptionRequestDTO dto = new AdoptionRequestDTO();
        dto.requestId = r.getId();
        dto.petId = r.getPet() != null ? r.getPet().getId() : null;
        dto.adopterId = r.getAdopter() != null ? r.getAdopter().getId() : null;
        dto.message = r.getMessage();
        dto.status = r.getStatus();
        dto.requestedAt = r.getRequestedAt();

        if (r.getPet() != null) {
            dto.petName = r.getPet().getPetName();
            dto.imageUrl = r.getPet().getImageUrl();
            dto.category = r.getPet().getCategory();
        }
        if (r.getAdopter() != null) {
            dto.adopterName = r.getAdopter().getFullName();
            dto.adopterEmail = r.getAdopter().getEmail();
            dto.adopterPhone = r.getAdopter().getPhone();
        }
        return dto;
    }

    public Integer getRequestId() { return requestId; }
    public void setRequestId(Integer requestId) { this.requestId = requestId; }

    public Integer getPetId() { return petId; }
    public void setPetId(Integer petId) { this.petId = petId; }

    public Integer getAdopterId() { return adopterId; }
    public void setAdopterId(Integer adopterId) { this.adopterId = adopterId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAdopterName() { return adopterName; }
    public void setAdopterName(String adopterName) { this.adopterName = adopterName; }

    public String getAdopterEmail() { return adopterEmail; }
    public void setAdopterEmail(String adopterEmail) { this.adopterEmail = adopterEmail; }

    public String getAdopterPhone() { return adopterPhone; }
    public void setAdopterPhone(String adopterPhone) { this.adopterPhone = adopterPhone; }
}
