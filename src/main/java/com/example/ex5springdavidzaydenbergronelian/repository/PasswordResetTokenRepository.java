package com.example.ex5springdavidzaydenbergronelian.repository;

import com.example.ex5springdavidzaydenbergronelian.model.PasswordResetToken;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PasswordResetToken
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}