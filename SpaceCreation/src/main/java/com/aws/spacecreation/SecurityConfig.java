package com.aws.spacecreation;

import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserService;
import com.aws.spacecreation.user.auth.CustomOAuth2UserService;
import com.aws.spacecreation.user.auth.CustomUserDetails;
import com.aws.spacecreation.user.auth.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/question/create", "/question/update", "/question/delete/**",
                                "/answer/create/**", "/like/**", "/interiorboard/interiorboardform").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/read/**",
                                "/interiorboard/interiorboarddetail/**",
                                "/interiorboard/toggleLike",
                                "/comment/**"
                        )
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/", true))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)));
                return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oAuth2User = customOAuth2UserService.loadUser(userRequest);
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            OAuthAttributes oauthAttributes = OAuthAttributes.of(registrationId, attributes);

            SiteUser user = userService.findOrCreateUserByEmail(oauthAttributes);
            return new CustomUserDetails(user, oAuth2User.getAttributes());
        };
    }
}
