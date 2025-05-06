package com.skillshare.skillsharebackend.repository;

import com.skillshare.skillsharebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    List<User> findByFullNameContainingIgnoreCase(String fullName);
}
