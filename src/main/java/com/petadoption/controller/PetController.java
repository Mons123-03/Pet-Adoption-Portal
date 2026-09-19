package com.petadoption.controller;

import com.petadoption.dto.PetDTO;
import com.petadoption.entity.Pet;
import com.petadoption.entity.User;
import com.petadoption.service.PetService;
import com.petadoption.service.UserService;
import com.petadoption.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    // Public: browse/search available pets
    @GetMapping("/pets")
    public Map<String, Object> listPets(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {

        List<Pet> pets = petService.searchPets(category, keyword, minAge, maxAge);
        List<PetDTO> dtos = pets.stream().map(PetDTO::fromEntity).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("pets", dtos);
        return response;
    }

    @GetMapping("/pet-detail")
    public Map<String, Object> getPetDetail(@RequestParam("id") Integer id) {
        Pet pet = petService.getById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("pet", PetDTO.fromEntity(pet));
        return response;
    }

    // Pet owner: view their own listings
    @GetMapping("/my-pets")
    public Map<String, Object> myPets(HttpSession session) {
        SessionUtil.requireRole(session, "OWNER");
        Integer ownerId = SessionUtil.requireUserId(session);

        List<Pet> pets = petService.getPetsByOwner(ownerId);
        List<PetDTO> dtos = pets.stream().map(PetDTO::fromEntity).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("pets", dtos);
        return response;
    }

    // Pet owner: add a new pet (multipart form with optional photo)
    @PostMapping("/pet-add")
    public Map<String, String> addPet(
            HttpSession session,
            @RequestParam("pet_name") String petName,
            @RequestParam("category") String category,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "age", required = false) String ageStr,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        SessionUtil.requireRole(session, "OWNER");
        Integer ownerId = SessionUtil.requireUserId(session);
        User owner = userService.getById(ownerId);

        Integer age = parseAge(ageStr);
        petService.addPet(owner, petName, category, breed, age, gender, description, image);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet listed successfully");
        return response;
    }

    // Pet owner: edit an existing pet they own
    @PostMapping("/pet-edit")
    public Map<String, String> editPet(
            HttpSession session,
            @RequestParam("pet_id") Integer petId,
            @RequestParam(value = "pet_name", required = false) String petName,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "age", required = false) String ageStr,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        SessionUtil.requireRole(session, "OWNER");
        Integer ownerId = SessionUtil.requireUserId(session);

        Integer age = parseAge(ageStr);
        petService.updatePet(petId, ownerId, petName, category, breed, age, gender, description, status, image);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet updated successfully");
        return response;
    }

    // Safely parses the age field, which the frontend may send as an empty string
    private Integer parseAge(String ageStr) {
        if (ageStr == null || ageStr.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(ageStr.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Pet owner: delete their own pet listing
    @PostMapping("/pet-delete")
    public Map<String, String> deletePet(HttpSession session, @RequestParam("pet_id") Integer petId) {
        SessionUtil.requireRole(session, "OWNER");
        Integer ownerId = SessionUtil.requireUserId(session);

        petService.deletePet(petId, ownerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet listing deleted");
        return response;
    }
}
