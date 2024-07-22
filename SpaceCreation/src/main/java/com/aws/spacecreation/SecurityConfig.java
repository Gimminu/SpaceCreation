package com.aws.spacecreation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/question/create","/question/update" ,"/question/delete/**",
                                "/answer/create/**","/like/**","/interiorboard/interiorboardform").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/read/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .formLogin(form -> form
                        .loginPage("/user/check-authentication")
                        .defaultSuccessUrl("/", true))
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/").permitAll());

        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManger(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
