package com.aws.spacecreation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    @GetMapping("/user/check-authentication")
    public Map<String, Boolean> checkAuthentication(Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        return Collections.singletonMap("isAuthenticated", isAuthenticated);
    }
}
