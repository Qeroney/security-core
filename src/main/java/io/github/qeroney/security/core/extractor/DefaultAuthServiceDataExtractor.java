package io.github.qeroney.security.core.extractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.qeroney.security.core.model.CustomUserDetails;
import io.github.qeroney.security.core.model.CustomUserDetailsImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(AuthServiceDataExtractor.class)
public class DefaultAuthServiceDataExtractor implements AuthServiceDataExtractor {

    private final ObjectMapper objectMapper;

    @Override
    public CustomUserDetails principalToCustomUserDetails(@NonNull OAuth2Authentication authentication, @NonNull Object principal) {
        return objectMapper.convertValue(principal, CustomUserDetailsImpl.class);
    }

    @Override
    public CustomUserDetails detailsMapToCustomUserDetails(@NonNull OAuth2Authentication authentication, @NonNull Map<String, Object> details) {
        return CustomUserDetailsImpl.builder()
                                    .id(UUID.fromString((String) details.get("id")))
                                    .username((String) details.get("username"))
                                    .password((String) details.get("password"))
                                    .authorities(new HashSet<>(authentication.getAuthorities()))
                                    .build();
    }

    @Override
    public PrincipalExtractor principalExtractor() {
        return map -> objectMapper.convertValue(map, CustomUserDetailsImpl.class);
    }
}
