package com.example.ex5springdavidzaydenbergronelian.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import static com.example.ex5springdavidzaydenbergronelian.constants.InfoMessages.*;

/**
 * User entity.
 */
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = USERNAME_MANDATORY)
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,20}$", message = USERNAME_VALIDATION)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = PASSWORD_MANDATORY)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = PASSWORD_VALIDATION)
    @Column(nullable = false)
    private String password;

    @NotBlank(message = EMAIL_MANDATORY)
    @Email(message = EMAIL_VALIDATION)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = FIRST_NAME_MANDATORY)
    @Size(min = 1, max = 50, message = FIRST_NAME_VALIDATION)
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = LAST_NAME_MANDATORY)
    @Size(min = 1, max = 50, message = LAST_NAME_VALIDATION)
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = DATE_OF_BIRTH_MANDATORY)
    @Past(message = DATE_OF_BIRTH_VALIDATION)
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^$|^[0-9]{1,}$", message = PHONE_NUMBER_VALIDATION)
    private String phoneNumber;

    @NotBlank(message = ROLE_MANDATORY)
    @Column(nullable = false)
    private String role = "ROLE_USER";

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @Column(nullable = false)
    private LocalDate lastPasswordResetDate;

    @PrePersist
    @PreUpdate
    private void prePersistAndUpdate() {
        if (lastPasswordResetDate == null) {
            lastPasswordResetDate = LocalDate.now();
        }
    }
}