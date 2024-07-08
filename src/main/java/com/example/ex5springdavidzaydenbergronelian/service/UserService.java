package com.example.ex5springdavidzaydenbergronelian.service;

import com.example.ex5springdavidzaydenbergronelian.model.PasswordResetToken;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.repository.PasswordResetTokenRepository;
import com.example.ex5springdavidzaydenbergronelian.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.ex5springdavidzaydenbergronelian.constants.ErrorMessages.USER_NOT_FOUND_WITH_USERNAME;

/**
 * Service class for managing users.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    /**
     * Constructor for the UserService class.
     * @param userRepository The repository for users.
     * @param passwordEncoder The password encoder.
     * @param passwordResetTokenRepository The repository for password reset tokens.
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    /**
     * Load a user by username.
     * @param username The username of the user to load.
     * @return UserDetails
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(USER_NOT_FOUND_WITH_USERNAME + username);

        return new CustomUserDetails(user);
    }

    /**
     * Find a user by username.
     * @param username The username of the user to find.
     * @return The user with the given username.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find a user by email.
     * @param email The email of the user to find.
     * @return The user with the given email.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find all users.
     * @return A list of all users.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Save a user.
     * @param user The user to save.
     */
    @Transactional
    public void save(@Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Update a user's password.
     * @param user The user to update.
     * @param newPassword The new password.
     */
    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Create a password reset token for a user.
     * @param email The email of the user.
     * @param token The token to create.
     * @return True if the token was created, false otherwise.
     */
    @Transactional
    public boolean createPasswordResetTokenForEmail(String email, String token) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        // Check if a token already exists for this user
        PasswordResetToken existingToken = passwordResetTokenRepository.findByUser(user);
        if (existingToken != null) {
            // Update the existing token
            existingToken.setToken(token);
            existingToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            passwordResetTokenRepository.save(existingToken);
        } else {
            // Create a new token
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setToken(token);
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            passwordResetTokenRepository.save(passwordResetToken);
        }

        return true;
    }

    /**
     * Validate a password reset token.
     * @param token The token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        return passwordResetToken != null && LocalDateTime.now().isBefore(passwordResetToken.getExpiryDate());
    }

    /**
     * Reset a user's password.
     * @param token The token to reset the password with.
     * @param newPassword The new password.
     * @return True if the password was reset, false otherwise.
     */
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken != null && LocalDateTime.now().isBefore(passwordResetToken.getExpiryDate())) {
            User user = passwordResetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            passwordResetTokenRepository.delete(passwordResetToken);
            return true;
        }
        return false;
    }

    /**
     * Find a user by ID.
     * @param userId The ID of the user to find.
     * @return The user with the given ID.
     */
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
