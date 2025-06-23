package io.github.qeroney.security.core.annotation;

import io.github.qeroney.security.core.config.GlobalMethodSecurityConfig;
import io.github.qeroney.security.core.config.JacksonConfig;
import io.github.qeroney.security.core.config.ResourceServerConfig;
import io.github.qeroney.security.core.config.SecurityConfig;
import io.github.qeroney.security.core.extractor.DefaultAuthServiceDataExtractor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DefaultAuthServiceDataExtractor.class, SecurityConfig.class, GlobalMethodSecurityConfig.class, ResourceServerConfig.class, JacksonConfig.class})
public @interface EnableSecurity {
}
