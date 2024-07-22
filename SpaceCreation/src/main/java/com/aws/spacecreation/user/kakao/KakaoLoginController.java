package com.aws.spacecreation.user.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class KakaoLoginController {
    private final KaKaoApi kakaoApi;

    @RequestMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam String code) {
        String accessToken = kakaoApi.getAccessToken(code);

        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        String nickname = (String) userInfo.get("nickname");

        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);
        System.out.println("accessToken = " + accessToken);

        return "redirect:/result";
    }
}