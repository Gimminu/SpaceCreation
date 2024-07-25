package com.aws.spacecreation.user.auth;

import com.aws.spacecreation.user.SiteUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    private final OAuthTokenService oAuthTokenService;
    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);


    @GetMapping("/oauth2/authorization/kakao")
    public String kakaoLogin(String code, HttpSession session) {
        try {
            ResponseEntity<String> response = oAuthTokenService.requestKakaoToken(code);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to retrieve token");
            }

            SiteUser user = oAuthService.processKakaoLogin(response.getBody());
            setSessionAndAuthentication(user, session);
            return "redirect:/home";
        }
        catch (OAuth2AuthenticationException e) {
            logger.error("OAuth2 Authentication failed: ", e);
            return "redirect:/user/login?error=" + e.getLocalizedMessage();
        }
        catch (Exception e) {
            logger.error("Error during Kakao login", e);
            return "redirect:/error";
        }
    }

    @GetMapping("/oauth2/authorization/naver")
    public String naverLogin(String code, HttpSession session) {
        try {
            String state = (String) session.getAttribute("state");
            ResponseEntity<String> response = oAuthTokenService.requestNaverToken(code, state);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to retrieve token");
            }

            SiteUser user = oAuthService.processNaverLogin(response.getBody());
            setSessionAndAuthentication(user, session);
            return "redirect:/home";
        } catch (Exception e) {
            logger.error("Error during Naver login", e);
            return "redirect:/error";
        }
    }
    /*
    @GetMapping("/oauth2/authorization/{registrationId}")
    public String oauth2Login(@PathVariable String registrationId, OAuth2AuthenticationToken authentication, HttpSession session) {
        try {
            logger.info("OAuth2 login request: registrationId={}, attributes={}", registrationId, authentication.getPrincipal().getAttributes());
            SiteUser user = oAuthService.registerOrUpdateUser(registrationId, authentication);
            setSessionAndAuthentication(user, session);
            return "redirect:/home";
        } catch (OAuth2AuthenticationException e) {
            logger.error("OAuth2 Authentication failed: ", e);
            return "redirect:/user/login?error=" + e.getLocalizedMessage();
        } catch (Exception e) {
            logger.error("Error during OAuth2 login", e);
            return "redirect:/error";
        }
    }
*/
    private void setSessionAndAuthentication(SiteUser user, HttpSession session) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute("user", user);
    }
}
