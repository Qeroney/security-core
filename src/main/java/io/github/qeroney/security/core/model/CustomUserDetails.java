package io.github.qeroney.security.core.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface CustomUserDetails extends UserDetails {
    UUID getId();
}