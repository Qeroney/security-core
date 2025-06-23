package io.github.qeroney.security.core.service;

import io.github.qeroney.security.core.extractor.AuthServiceDataExtractor;
import io.github.qeroney.security.core.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthServiceDataExtractor extractor;

    @Override
    public Optional<UUID> getUserId() {
        return getUserDetails().map(CustomUserDetails::getId);
    }

    @Override
    public UUID getAuthorizedUserId() {
        return getUserId()
                .orElseThrow(() -> new AuthenticationServiceException("Пользователь не авторизован"));
    }

    @Override
    public Set<String> getAuthorities() {
        return getUserDetails()
                .map(UserDetails::getAuthorities)
                .map(authorities -> authorities.stream()
                                               .map(GrantedAuthority::getAuthority)
                                               .filter(Objects::nonNull)
                                               .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    @Override
    public Optional<CustomUserDetails> getUserDetails() {
        try {
            Optional<CustomUserDetails> userDetails = getPrincipal();
            return userDetails.isPresent() ? userDetails : getDetails();
        } catch (Exception e) {
            log.error("Failed to get user details", e);
            return Optional.empty();
        }
    }

    @Override
    public CustomUserDetails getAuthorizedUserDetails() {
        return getUserDetails()
                .orElseThrow(() -> new AuthenticationServiceException("Пользователь не авторизован"));
    }

    private Optional<CustomUserDetails> getPrincipal() {
        return getOAuth2Authentication()
                .flatMap(extractor::extractUserDetailsUsingPrincipal);
    }

    private Optional<CustomUserDetails> getDetails() {
        return getOAuth2Authentication()
                .flatMap(extractor::extractUserDetailsUsingDetails);
    }

    private Optional<OAuth2Authentication> getOAuth2Authentication() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                log.debug("No authentication found in SecurityContext");
                return Optional.empty();
            }

            if (!(authentication instanceof OAuth2Authentication)) {
                log.debug("Authentication is not OAuth2Authentication: {}",
                          authentication.getClass().getSimpleName());
                return Optional.empty();
            }

            return Optional.of((OAuth2Authentication) authentication);
        } catch (Exception e) {
            log.error("Error getting OAuth2Authentication", e);
            return Optional.empty();
        }
    }
}