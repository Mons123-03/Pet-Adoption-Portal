package com.petadoption.service;

import com.petadoption.entity.Pet;
import com.petadoption.entity.User;
import com.petadoption.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Browse/search AVAILABLE pets. Filtering is done in plain Java after
     * fetching the AVAILABLE list, which keeps the query simple (no dynamic
     * JPQL) - fine for a small student-project dataset.
     */
    public List<Pet> searchPets(String category, String keyword, Integer minAge, Integer maxAge) {
        List<Pet> pets = petRepository.findByStatusOrderByCreatedAtDesc("AVAILABLE");

        return pets.stream()
                .filter(p -> category == null || category.isBlank() || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> keyword == null || keyword.isBlank() || matchesKeyword(p, keyword))
                .filter(p -> minAge == null || p.getAge() >= minAge)
                .filter(p -> maxAge == null || p.getAge() <= maxAge)
                .toList();
    }

    private boolean matchesKeyword(Pet p, String keyword) {
        String k = keyword.toLowerCase();
        return (p.getPetName() != null && p.getPetName().toLowerCase().contains(k)) ||
               (p.getBreed() != null && p.getBreed().toLowerCase().contains(k)) ||
               (p.getDescription() != null && p.getDescription().toLowerCase().contains(k));
    }

    public List<Pet> getPetsByOwner(Integer ownerId) {
        return petRepository.findByOwnerIdOrderByCreatedAtDesc(ownerId);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAllByOrderByCreatedAtDesc();
    }

    public Pet getById(Integer petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Pet not found"));
    }

    public Pet addPet(User owner, String petName, String category, String breed, Integer age,
                       String gender, String description, MultipartFile image) {

        if (petName == null || petName.trim().isEmpty() || category == null || category.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Pet name and category are required");
        }

        Pet pet = new Pet();
        pet.setOwner(owner);
        pet.setPetName(petName);
        pet.setCategory(category);
        pet.setBreed(breed);
        pet.setAge(age != null ? age : 0);
        pet.setGender(gender == null || gender.isBlank() ? "Male" : gender);
        pet.setDescription(description);

        String imageUrl = fileStorageService.saveFile(image);
        pet.setImageUrl(imageUrl);

        return petRepository.save(pet);
    }

    public Pet updatePet(Integer petId, Integer ownerId, String petName, String category, String breed,
                          Integer age, String gender, String description, String status, MultipartFile image) {

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Pet not found"));

        if (!pet.getOwner().getId().equals(ownerId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You do not own this listing");
        }

        if (petName != null && !petName.isBlank()) pet.setPetName(petName);
        if (category != null && !category.isBlank()) pet.setCategory(category);
        if (breed != null) pet.setBreed(breed);
        if (age != null) pet.setAge(age);
        if (gender != null && !gender.isBlank()) pet.setGender(gender);
        if (description != null) pet.setDescription(description);
        if (status != null && !status.isBlank()) pet.setStatus(status);

        String newImageUrl = fileStorageService.saveFile(image);
        if (newImageUrl != null) {
            pet.setImageUrl(newImageUrl);
        }

        return petRepository.save(pet);
    }

    public void deletePet(Integer petId, Integer ownerId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Pet not found"));
        if (!pet.getOwner().getId().equals(ownerId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You do not own this listing");
        }
        petRepository.delete(pet);
    }

    public void deletePetAsAdmin(Integer petId) {
        if (!petRepository.existsById(petId)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Pet not found");
        }
        petRepository.deleteById(petId);
    }

    public void updateStatus(Integer petId, String status) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Pet not found"));
        pet.setStatus(status);
        petRepository.save(pet);
    }

    public long countByStatus(String status) {
        return petRepository.countByStatus(status);
    }

    public long countAll() {
        return petRepository.count();
    }
}
