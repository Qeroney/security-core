package io.github.qeroney.security.core.config;

import io.github.qeroney.security.core.extractor.AuthServiceDataExtractor;
import io.github.qeroney.security.core.service.AuthService;
import io.github.qeroney.security.core.service.AuthServiceImpl;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class SecurityConfig {

    @Bean
    public PrincipalExtractor principalExtractor(AuthServiceDataExtractor extractor) {
        return extractor.principalExtractor();
    }

    @Bean
    public AuthService authService(AuthServiceDataExtractor extractor) {
        return new AuthServiceImpl(extractor);
    }
}
