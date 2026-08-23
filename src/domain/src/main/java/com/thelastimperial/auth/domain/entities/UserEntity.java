package com.thelastimperial.auth.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.thelastimperial.utils.entities.Username;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name="users")
public class UserEntity implements Username {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id") },
        inverseJoinColumns = {@JoinColumn(name = "role_id") },
        uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "role_id" }) }
    )
    private List<UserRoleEntity> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserActivationEntity> activations;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserExpiryEntity expiry;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRecoveryEntity> recoveries;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
