package com.example.lms.repository;

import com.example.lms.model.Profile;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Optional<Profile> findByUser(User user);
}
