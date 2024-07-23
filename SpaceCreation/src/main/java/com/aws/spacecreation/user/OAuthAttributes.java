package com.aws.spacecreation.user;

import java.util.Map;
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    private OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        throw new IllegalArgumentException("Unsupported registration id: " + registrationId);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String name = (String) profile.get("nickname");
        String email = (String) kakaoAccount.get("email");
        String picture = (String) profile.get("profile_image_url");

        return new OAuthAttributes(attributes, userNameAttributeName, name, email);
    }

    public SiteUser toEntity() {
        SiteUser user = new SiteUser();
        user.setUsername(this.name);
        user.setEmail(this.email);
        user.setUserRole(UserRole.USER);
        return user;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getNameAttributeKey() {
        return nameAttributeKey;
    }
}
