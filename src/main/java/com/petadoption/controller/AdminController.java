package com.petadoption.controller;

import com.petadoption.dto.AdoptionRequestDTO;
import com.petadoption.dto.PetDTO;
import com.petadoption.dto.UserDTO;
import com.petadoption.entity.AdoptionRequest;
import com.petadoption.entity.Pet;
import com.petadoption.entity.User;
import com.petadoption.service.AdoptionRequestService;
import com.petadoption.service.PetService;
import com.petadoption.service.UserService;
import com.petadoption.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private AdoptionRequestService requestService;

    // ---------- Users ----------

    @GetMapping("/users")
    public Map<String, Object> getUsers(HttpSession session) {
        SessionUtil.requireRole(session, "ADMIN");

        List<User> users = userService.getAllUsers();
        List<UserDTO> dtos = users.stream().map(UserDTO::fromEntity).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("users", dtos);
        return response;
    }

    @PostMapping("/users")
    public Map<String, String> deleteUser(HttpSession session, @RequestParam("user_id") Integer userId) {
        SessionUtil.requireRole(session, "ADMIN");

        userService.deleteUser(userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted");
        return response;
    }

    // ---------- Pets ----------

    @GetMapping("/pets")
    public Map<String, Object> getPets(HttpSession session) {
        SessionUtil.requireRole(session, "ADMIN");

        List<Pet> pets = petService.getAllPets();
        List<PetDTO> dtos = pets.stream().map(PetDTO::fromEntity).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("pets", dtos);
        return response;
    }

    @PostMapping("/pets")
    public Map<String, String> deletePet(HttpSession session, @RequestParam("pet_id") Integer petId) {
        SessionUtil.requireRole(session, "ADMIN");

        petService.deletePetAsAdmin(petId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet listing removed");
        return response;
    }

    // ---------- Adoption Requests ----------

    @GetMapping("/requests")
    public Map<String, Object> getRequests(HttpSession session) {
        SessionUtil.requireRole(session, "ADMIN");

        List<AdoptionRequest> requests = requestService.getAllRequests();
        List<AdoptionRequestDTO> dtos = requests.stream().map(AdoptionRequestDTO::fromEntity).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("requests", dtos);
        return response;
    }

    @PostMapping("/requests")
    public Map<String, String> updateRequest(
            HttpSession session,
            @RequestParam("request_id") Integer requestId,
            @RequestParam("status") String status) {

        SessionUtil.requireRole(session, "ADMIN");

        requestService.updateStatusAsAdmin(requestId, status);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Request " + status.toLowerCase());
        return response;
    }

    // ---------- Reports ----------

    @GetMapping("/reports")
    public Map<String, Object> getReports(HttpSession session) {
        SessionUtil.requireRole(session, "ADMIN");

        Map<String, Object> userStats = new HashMap<>();
        userStats.put("total_users", userService.countAll());
        userStats.put("total_adopters", userService.countByRole("ADOPTER"));
        userStats.put("total_owners", userService.countByRole("OWNER"));

        Map<String, Object> petStats = new HashMap<>();
        petStats.put("total_pets", petService.countAll());
        petStats.put("available_pets", petService.countByStatus("AVAILABLE"));
        petStats.put("pending_pets", petService.countByStatus("PENDING"));
        petStats.put("adopted_pets", petService.countByStatus("ADOPTED"));

        Map<String, Object> requestStats = new HashMap<>();
        requestStats.put("total_requests", requestService.countAll());
        requestStats.put("pending_requests", requestService.countByStatus("PENDING"));
        requestStats.put("approved_requests", requestService.countByStatus("APPROVED"));
        requestStats.put("rejected_requests", requestService.countByStatus("REJECTED"));

        Map<String, Object> response = new HashMap<>();
        response.put("userStats", userStats);
        response.put("petStats", petStats);
        response.put("requestStats", requestStats);
        return response;
    }
}
