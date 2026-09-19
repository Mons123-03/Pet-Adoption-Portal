package com.petadoption.repository;

import com.petadoption.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByStatusOrderByCreatedAtDesc(String status);

    List<Pet> findByOwnerIdOrderByCreatedAtDesc(Integer ownerId);

    List<Pet> findAllByOrderByCreatedAtDesc();

    long countByStatus(String status);
}
