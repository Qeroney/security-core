package io.github.qeroney.security.core.extractor;

import io.github.qeroney.security.core.model.CustomUserDetails;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;
import java.util.Optional;

public interface AuthServiceDataExtractor {

    default Optional<CustomUserDetails> extractUserDetailsUsingPrincipal(OAuth2Authentication authentication) {
        Object principal = authentication.getUserAuthentication().getPrincipal();
        if (principal == null) {
            return Optional.empty();
        } else {
            CustomUserDetails customUserDetails = this.principalToCustomUserDetails(authentication, principal);
            return Optional.of(customUserDetails);
        }
    }

    CustomUserDetails principalToCustomUserDetails(@NonNull OAuth2Authentication var1, @NonNull Object var2);

    default Optional<CustomUserDetails> extractUserDetailsUsingDetails(OAuth2Authentication authentication) {
        Object details = authentication.getUserAuthentication().getDetails();
        if (details instanceof CustomUserDetails) {
            return Optional.of((CustomUserDetails)details);
        } else if (details instanceof Map) {
            Map<String, Object> detailsMap = (Map)details;
            CustomUserDetails customUserDetails = this.detailsMapToCustomUserDetails(authentication, detailsMap);
            return Optional.of(customUserDetails);
        } else {
            return Optional.empty();
        }
    }

    CustomUserDetails detailsMapToCustomUserDetails(@NonNull OAuth2Authentication var1, @NonNull Map<String, Object> var2);

    PrincipalExtractor principalExtractor();
}
