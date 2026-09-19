package com.petadoption.repository;

import com.petadoption.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {

    List<AdoptionRequest> findByAdopterIdOrderByRequestedAtDesc(Integer adopterId);

    // Nested property traversal: AdoptionRequest -> pet -> owner -> id
    List<AdoptionRequest> findByPet_Owner_IdOrderByRequestedAtDesc(Integer ownerId);

    List<AdoptionRequest> findAllByOrderByRequestedAtDesc();

    List<AdoptionRequest> findByPetIdAndAdopterIdAndStatus(Integer petId, Integer adopterId, String status);

    // Used to auto-reject any other pending requests for the same pet once one is approved
    List<AdoptionRequest> findByPetIdAndStatusAndIdNot(Integer petId, String status, Integer excludeId);

    long countByStatus(String status);
}
