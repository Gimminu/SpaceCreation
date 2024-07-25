package com.aws.spacecreation.user;

import com.aws.spacecreation.user.auth.OAuthAttributes;
import com.aws.spacecreation.user.auth.UserProvider;
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
    public SiteUser create(UserCreateForm userCreateForm) {
        Optional<SiteUser> userOptional = userRepository.findByEmail(userCreateForm.getEmail());
        if(userOptional.isPresent()){
            SiteUser existingUser = userOptional.get();
            existingUser.setNickname(userCreateForm.getNickname());
            existingUser.setPassword(passwordEncoder.encode(userCreateForm.getPassword1()));
            existingUser.setEmail(userCreateForm.getEmail());
            return userRepository.save(existingUser);
        }
        else{
            SiteUser user = new SiteUser();
            user.setNickname(userCreateForm.getNickname());
            user.setUserRole(UserRole.USER);
            user.setPassword(passwordEncoder.encode(userCreateForm.getPassword1()));
            user.setUsername(userCreateForm.getUsername());
            user.setEmail(userCreateForm.getEmail());
            user.setProvider(UserProvider.COMMON.getValue());
            return userRepository.save(user);
        }


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
