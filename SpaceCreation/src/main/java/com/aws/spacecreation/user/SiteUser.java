package com.aws.spacecreation.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser implements OAuth2User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String nickname;
    private String password;
    private String provider;

    @Column(unique = true)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", this.username);
        attributes.put("nickname", this.nickname);
        attributes.put("email", this.email);
        attributes.put("provider", this.provider);
        return attributes;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.userRole.name()));
    }
}
