package com.petadoption.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adoption_requests")
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopter_id", nullable = false)
    private User adopter;

    @Column(columnDefinition = "TEXT")
    private String message;

    // PENDING, APPROVED, or REJECTED
    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    @Column(updatable = false)
    private LocalDateTime requestedAt = LocalDateTime.now();

    public AdoptionRequest() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public User getAdopter() { return adopter; }
    public void setAdopter(User adopter) { this.adopter = adopter; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }
}
