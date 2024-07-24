package com.aws.spacecreation.user.auth;

import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);

    public SiteUser registerOrUpdateUser(String registrationId, OAuth2AuthenticationToken authentication) {
        try {
            logger.info("Registering or updating user: registrationId={}, attributes={}", registrationId, authentication.getPrincipal().getAttributes());
            OAuthAttributes attributes = OAuthAttributes.of(registrationId, authentication.getPrincipal().getAttributes());

            Optional<SiteUser> existingUser = userService.findByEmail(attributes.getEmail());
            return existingUser
                    .map(user -> {
                        user.setNickname(attributes.getName());
                        return userService.createOrUpdateUser(user);
                    })
                    .orElseGet(() -> userService.createOrUpdateUser(attributes.toEntity()));
        } catch (OAuth2AuthenticationException e) {
            logger.error("OAuth2AuthenticationException during user registration or update", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error during user registration or update", e);
            throw new RuntimeException("Error during user registration or update: " + e.getMessage(), e);
        }
    }

    public SiteUser processKakaoLogin(String responseBody) {
        JSONObject jsonParser = new JSONObject(responseBody);
        JSONObject properties = jsonParser.getJSONObject("properties");
        JSONObject kakaoAccount = jsonParser.getJSONObject("kakao_account");

        String email = kakaoAccount.getString("email");
        String userName = properties.getString("nickname");

        OAuthAttributes attributes = new OAuthAttributes(Map.of(
                "email", email,
                "name", userName,
                "registrationId", "kakao"
        ), "kakao", userName, email, "KAKAO");

        return userService.findOrCreateUserByEmail(attributes);
    }

    public SiteUser processNaverLogin(String responseBody) {
        JSONObject jsonParser = new JSONObject(responseBody);
        JSONObject response = jsonParser.getJSONObject("response");

        String email = response.getString("email");
        String userName = response.getString("name");

        OAuthAttributes attributes = new OAuthAttributes(Map.of(
                "email", email,
                "name", userName,
                "registrationId", "naver"
        ), "naver", userName, email, "NAVER");

        return userService.findOrCreateUserByEmail(attributes);
    }
}
