package com.petadoption.repository;

import com.petadoption.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    long countByRole(String role);

    List<User> findAllByOrderByCreatedAtDesc();
}
