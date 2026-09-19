package com.petadoption.service;

import com.petadoption.entity.AdoptionRequest;
import com.petadoption.entity.Pet;
import com.petadoption.entity.User;
import com.petadoption.repository.AdoptionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionRequestService {

    @Autowired
    private AdoptionRequestRepository requestRepository;

    @Autowired
    private PetService petService;

    public AdoptionRequest submitRequest(User adopter, Integer petId, String message) {
        Pet pet = petService.getById(petId);

        if (!"AVAILABLE".equals(pet.getStatus())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "This pet is not available for adoption");
        }

        List<AdoptionRequest> existing = requestRepository.findByPetIdAndAdopterIdAndStatus(petId, adopter.getId(), "PENDING");
        if (!existing.isEmpty()) {
            throw new ApiException(HttpStatus.CONFLICT, "You already have a pending request for this pet");
        }

        AdoptionRequest request = new AdoptionRequest();
        request.setPet(pet);
        request.setAdopter(adopter);
        request.setMessage(message);
        request.setStatus("PENDING");

        AdoptionRequest saved = requestRepository.save(request);

        petService.updateStatus(petId, "PENDING");

        return saved;
    }

    public List<AdoptionRequest> getRequestsByAdopter(Integer adopterId) {
        return requestRepository.findByAdopterIdOrderByRequestedAtDesc(adopterId);
    }

    public List<AdoptionRequest> getRequestsForOwner(Integer ownerId) {
        return requestRepository.findByPet_Owner_IdOrderByRequestedAtDesc(ownerId);
    }

    public List<AdoptionRequest> getAllRequests() {
        return requestRepository.findAllByOrderByRequestedAtDesc();
    }

    public AdoptionRequest getById(Integer requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Request not found"));
    }

    /**
     * Owner approves/rejects a request for one of their own pets.
     * ownerId is checked against the pet's actual owner for security.
     */
    public void updateStatusAsOwner(Integer requestId, Integer ownerId, String status) {
        AdoptionRequest request = getById(requestId);

        if (!request.getPet().getOwner().getId().equals(ownerId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You do not own the pet for this request");
        }

        applyStatusChange(request, status);
    }

    /** Admin can approve/reject any request without an ownership check. */
    public void updateStatusAsAdmin(Integer requestId, String status) {
        AdoptionRequest request = getById(requestId);
        applyStatusChange(request, status);
    }

    private void applyStatusChange(AdoptionRequest request, String status) {
        if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Status must be APPROVED or REJECTED");
        }

        request.setStatus(status);
        requestRepository.save(request);

        Integer petId = request.getPet().getId();

        if (status.equals("APPROVED")) {
            petService.updateStatus(petId, "ADOPTED");

            // Auto-reject any other pending requests for the same pet
            List<AdoptionRequest> others = requestRepository.findByPetIdAndStatusAndIdNot(petId, "PENDING", request.getId());
            for (AdoptionRequest other : others) {
                other.setStatus("REJECTED");
                requestRepository.save(other);
            }
        } else {
            petService.updateStatus(petId, "AVAILABLE");
        }
    }

    public long countByStatus(String status) {
        return requestRepository.countByStatus(status);
    }

    public long countAll() {
        return requestRepository.count();
    }
}
