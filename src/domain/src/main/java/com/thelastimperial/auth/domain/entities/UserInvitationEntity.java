package com.thelastimperial.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * UserInvitationEntity Register to allow create user by invitation.
*/
@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table (name="user_invitations")
public class UserInvitationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Who send the invitation.
    */
   @OneToOne
   @JoinColumn(name = "host_id", referencedColumnName = "id")
    private UserEntity host;
    /**
     * Who receive the invitation.
    */
   @OneToOne
   @JoinColumn(name = "guest_id", referencedColumnName = "id", nullable = true)
    private UserEntity guest;
    /**
     * To who is the invitation.
    */
    private String username;
    /**
     * To know when a invitation is used.
    */
   @ColumnDefault("false")
    private boolean isUsed;
    /**
     * The expiration must be used before the date.
    */
    private Instant expiredAt;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
