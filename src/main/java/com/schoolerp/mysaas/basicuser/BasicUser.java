package com.schoolerp.mysaas.basicuser;

import com.schoolerp.mysaas.common.dto.AuditableEntity;
import com.schoolerp.mysaas.school.School;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "basicuser")
public class BasicUser extends AuditableEntity {
    // 🏫 Multi-tenant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // 👤 Identity & Profile
    private String firstName;
    private String lastName;
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true)
    private String username;
    private String phone;

    // 🔐 Auth
    private String password;
    private String passwordSalt;
    private String role;
    private Boolean isActive = true;
    private Boolean isBlocked = false;
    private Boolean isEmailVerified = false;
    private Integer loginAttempts = 0;
    private LocalDateTime lastLoginAt;

    // 🌐 Preferences & Device
    @Column(name = "preferred_language")
    private String preferredLanguage = "en";
    private String firebaseUid;
    private String deviceToken;
    private String ipAddress;
    private String userAgent;

    // 🖼️ Personal Info
    private String profileImageUrl;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private String address;

    // 🔐 Security & 2FA
    private LocalDateTime passwordChangedAt;
    private Boolean twoFactorEnabled = false;
    private String twoFactorSecret;
    private LocalDateTime passwordResetRequestedAt;

    // 🔗 Social Auth
    private String googleId;
    private String facebookId;
    private String oauthProvider;

    // 🔒 Lock & Roles
    private LocalDateTime accountLockedUntil;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    private LocalDateTime lastActivityAt;
}
