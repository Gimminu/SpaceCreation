package com.aws.spacecreation.user;

import static com.aws.spacecreation.user.UserRole.ADMIN;
import static com.aws.spacecreation.user.UserRole.USER;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserRole(USER);
        this.userRepository.save(user);
    }

    public void update(String username, String email, String password) {
        Optional<SiteUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            SiteUser user = userOpt.get();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("사용자가 존재하지않습니다");
        }
    }

    public void withdraw(SiteUser siteUser) {
        userRepository.delete(siteUser);
    }

    @PostConstruct
    public void init() {
        createAdminIfNotExists();
    }

    private void createAdminIfNotExists() {
        Optional<SiteUser> adminOpt = userRepository.findByUsername("admin");
        if (adminOpt.isEmpty()) {
            SiteUser admin = new SiteUser();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // 기본 비밀번호
            admin.setUserRole(ADMIN);
            this.userRepository.save(admin);
        }
    }
}
