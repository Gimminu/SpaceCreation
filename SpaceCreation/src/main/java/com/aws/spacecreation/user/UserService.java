package com.aws.spacecreation.user;

import com.aws.spacecreation.user.auth.OAuthAttributes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Transactional
    public SiteUser createOrUpdateUser(SiteUser user) {

        return userRepository.findByEmail(user.getEmail())
                .map(existingUser -> {
                    existingUser.setNickname(user.getNickname());
                    existingUser.setProvider(user.getProvider());
                    existingUser.setUserRole(UserRole.USER);
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> userRepository.save(user));
    }

    public Optional<SiteUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void withdraw(SiteUser user) {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage(), e);
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public SiteUser findOrCreateUserByEmail(OAuthAttributes attributes) {
        SiteUser user = userRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());
        if (user.getId() == null) {
            userRepository.save(user);
        }
        return user;
    }
}
