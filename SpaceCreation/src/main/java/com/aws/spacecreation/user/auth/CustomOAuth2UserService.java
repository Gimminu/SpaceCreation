package com.aws.spacecreation.user.auth;

import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            OAuth2Error error = new OAuth2Error("oauth2_error", "An error occurred while processing OAuth2 user", null);
            throw new OAuth2AuthenticationException(error, ex);
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthAttributes oAuthAttributes = new OAuthAttributes(attributes);

        SiteUser user = userService.findOrCreateUserByEmail(oAuthAttributes);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (user.getUserRole() != null) {
            authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        }
        return new DefaultOAuth2User(authorities, attributes, user.getEmail());
    }
}

