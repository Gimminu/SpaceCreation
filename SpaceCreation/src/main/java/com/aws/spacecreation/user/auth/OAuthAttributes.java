package com.aws.spacecreation.user.auth;

import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserRole;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String registrationId;
    private String name;
    private String email;
    private String provider;

    public OAuthAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    public OAuthAttributes(Map<String, Object> attributes, String registrationId, String name, String email, String provider) {
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.name = name;
        this.email = email;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        log.info("Creating OAuthAttributes for registrationId={}, attributes={}", registrationId, attributes);
        switch (registrationId) {
            case "google":
                return ofGoogle(attributes);
            case "kakao":
                return ofKakao(attributes);
            case "naver":
                return ofNaver(attributes);
            case "facebook":
                return ofFacebook(attributes);
            default:
                throw new IllegalArgumentException("Unknown registrationId: " + registrationId);
        }
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");

        log.info("Google attributes: name={}, email={}", name, email);

        return new OAuthAttributes(attributes, "google", name, email, "GOOGLE");
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        String name = (String) properties.get("nickname");
        String email = (String) kakaoAccount.get("email");

        log.info("Kakao attributes: name={}, email={}", name, email);

        return new OAuthAttributes(attributes, "kakao", name, email, "KAKAO");
    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String name = (String) response.get("name");
        String email = (String) response.get("email");

        log.info("Naver attributes: name={}, email={}", name, email);

        return new OAuthAttributes(attributes, "naver", name, email, "NAVER");
    }

    private static OAuthAttributes ofFacebook(Map<String, Object> attributes) {
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");

        log.info("Facebook attributes: name={}, email={}", name, email);

        return new OAuthAttributes(attributes, "facebook", name, email, "FACEBOOK");
    }

    public SiteUser toEntity() {
        return SiteUser.builder()
                .username(email)
                .nickname(name)
                .email(email)
                .provider(provider)
                .userRole(UserRole.USER)
                .build();
    }
}
