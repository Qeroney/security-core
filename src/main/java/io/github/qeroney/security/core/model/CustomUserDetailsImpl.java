package io.github.qeroney.security.core.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomUserDetailsImpl implements CustomUserDetails {

    private UUID id;
    private String username;
    private String password;
    private Set<? extends GrantedAuthority> authorities = new HashSet<>();

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
}
