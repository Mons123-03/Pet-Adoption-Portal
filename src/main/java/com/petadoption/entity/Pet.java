package com.petadoption.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 100)
    private String petName;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 100)
    private String breed;

    private int age;

    @Column(length = 10)
    private String gender = "Male";

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    // AVAILABLE, PENDING, or ADOPTED
    @Column(nullable = false, length = 20)
    private String status = "AVAILABLE";

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Pet() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

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
