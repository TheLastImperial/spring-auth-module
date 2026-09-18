package com.thelastimperial.auth.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thelastimperial.auth.domain.entities.UserRegisteredClientEntity;
import com.thelastimperial.auth.domain.responses.OAuthClient;

public interface UserRegisteredClientRepository
    extends JpaRepository<UserRegisteredClientEntity, UUID> {
    @Query(nativeQuery = true,
        value =
        """
            SELECT orc.redirect_uris, orc.post_logout_redirect_uris, orc.client_secret_expires_at,
                orc.client_id, orc.client_secret
            FROM oauth2_registered_client orc
            INNER JOIN user_registered_clients urc
                ON urc.registered_client_id = orc.id
            WHERE user_id = uuid(?1)
        """
    )
    public List<OAuthClient> findClientsByUser(String userId);
    @Query(nativeQuery = true,
        value =
        """
            SELECT orc.redirect_uris, orc.post_logout_redirect_uris, orc.client_secret_expires_at,
                orc.client_id, orc.client_secret
            FROM oauth2_registered_client orc
            INNER JOIN user_registered_clients urc
                ON urc.registered_client_id = orc.id
            WHERE user_id = uuid(?1)
            ORDER BY urc.registered_client_id
            /*:pageable*/
        """,
        countQuery = """
            SELECT COUNT(*)
            FROM oauth2_registered_client orc
            INNER JOIN user_registered_clients urc
                ON urc.registered_client_id = orc.id
            WHERE user_id = uuid(?1)
        """
    )
    public Page<OAuthClient> findClientsByUser(String userId, Pageable pageable);
}
