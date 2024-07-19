package com.aws.spacecreation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
    }

    public void update(String username, String email, String password){
        Optional<SiteUser> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            SiteUser user = userOpt.get();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
        else{
            throw new IllegalArgumentException("사용자가 존재하지않습니다");
        }

    }

    public void withdraw(SiteUser siteUser){

        userRepository.delete(siteUser);
    }
}
