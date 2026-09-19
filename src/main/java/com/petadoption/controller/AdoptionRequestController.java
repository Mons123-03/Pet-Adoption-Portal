package com.petadoption.controller;

import com.petadoption.dto.AdoptionRequestDTO;
import com.petadoption.entity.AdoptionRequest;
import com.petadoption.entity.User;
import com.petadoption.service.AdoptionRequestService;
import com.petadoption.service.UserService;
import com.petadoption.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdoptionRequestController {

    @Autowired
    private AdoptionRequestService requestService;

    @Autowired
    private UserService userService;

    // Adopter: submit a new adoption request
    @PostMapping("/request-submit")
    public Map<String, String> submitRequest(
            HttpSession session,
            @RequestParam("pet_id") Integer petId,
            @RequestParam(value = "message", required = false) String message) {

        SessionUtil.requireRole(session, "ADOPTER");
        Integer adopterId = SessionUtil.requireUserId(session);
        User adopter = userService.getById(adopterId);

        requestService.submitRequest(adopter, petId, message);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Adoption request submitted");
        return response;
    }

    // Adopter: view their own requests
    @GetMapping("/my-requests")
    public Map<String, Object> myRequests(HttpSession session) {
        SessionUtil.requireRole(session, "ADOPTER");
        Integer adopterId = SessionUtil.requireUserId(session);

        List<AdoptionRequest> requests = requestService.getRequestsByAdopter(adopterId);
        List<AdoptionRequestDTO> dtos = requests.stream().map(AdoptionRequestDTO::fromEntity).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("requests", dtos);
        return response;
    }

    // Pet owner: view requests received for pets they own
    @GetMapping("/received-requests")
    public Map<String, Object> receivedRequests(HttpSession session) {
        SessionUtil.requireRole(session, "OWNER");
        Integer ownerId = SessionUtil.requireUserId(session);

        List<AdoptionRequest> requests = requestService.getRequestsForOwner(ownerId);
        List<AdoptionRequestDTO> dtos = requests.stream().map(AdoptionRequestDTO::fromEntity).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("requests", dtos);
        return response;
    }

    // Pet owner: approve or reject a request for their pet
    @PostMapping("/request-update")
    public Map<String, String> updateRequest(
            HttpSession session,
            @RequestParam("request_id") Integer requestId,
            @RequestParam("status") String status) {

        SessionUtil.requireRole(session, "OWNER");
        Integer ownerId = SessionUtil.requireUserId(session);

        requestService.updateStatusAsOwner(requestId, ownerId, status);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Request " + status.toLowerCase());
        return response;
    }
}
