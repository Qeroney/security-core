package io.github.qeroney.security.core.service;

import io.github.qeroney.security.core.model.CustomUserDetails;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AuthService {
    Optional<UUID> getUserId();

    UUID getAuthorizedUserId();

    Set<String> getAuthorities();

    Optional<CustomUserDetails> getUserDetails();

    CustomUserDetails getAuthorizedUserDetails();
}
