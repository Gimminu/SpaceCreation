package com.aws.spacecreation.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserSecuritySerivce userSecuritySerivce;

    //회원가입
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "view/accounts/signup_form";
    }

    //로그인
    @GetMapping("/login")
    public String login(){
        return "view/accounts/login_form";
    }

    @GetMapping("/update")
    public String update(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            throw new IllegalStateException("접속자 타입 오류");
        }

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String email = oauthToken.getPrincipal().getAttributes().get("email").toString();
        SiteUser siteUser = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        UserCreateForm userCreateForm = new UserCreateForm();
        userCreateForm.setUsername(siteUser.getUsername());
        userCreateForm.setEmail(siteUser.getEmail());
        userCreateForm.setNickname(siteUser.getNickname());
        model.addAttribute("userCreateForm", userCreateForm);
        return "view/accounts/profile_management";
    }

    //회원가입
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "view/accounts/signup_form";
        }

        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다");
            return "view/accounts/signup_form";
        }

        SiteUser newUser = new SiteUser();
        newUser.setUsername(userCreateForm.getUsername());
        newUser.setNickname(userCreateForm.getNickname());
        newUser.setEmail(userCreateForm.getEmail());
        newUser.setPassword(userCreateForm.getPassword1());

        try {
            userService.createOrUpdateUser(newUser);
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "view/accounts/signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "view/accounts/signup_form";
        }
        return "redirect:/user/login";
    }

    //회원 정보 수정
    @PostMapping("/update")
    public String update(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "view/accounts/profile_management";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "view/accounts/profile_management";
        }

        SiteUser updateUser = new SiteUser();
        updateUser.setUsername(userCreateForm.getUsername());
        updateUser.setNickname(userCreateForm.getNickname());
        updateUser.setEmail(userCreateForm.getEmail());
        updateUser.setPassword(userCreateForm.getPassword1());

        try {
            userService.createOrUpdateUser(updateUser);
        } catch (Exception e) {
            bindingResult.rejectValue("username", "updateFailed", "사용자 정보 업데이트에 실패했습니다.");
            return "view/accounts/profile_management";
        }

        return "view/index";
    }

    //회원 탈퇴
    @GetMapping("/withdraw")
    public String withdraw() {
        try {
            userService.withdraw(userSecuritySerivce.getAuthen());
        } catch (Exception e) {
            return e.getMessage();
        }
        return "redirect:/user/logout";
    }

    //로그인 상태 확인
    @GetMapping("/api/check-authentication")
    public Map<String, Boolean> checkAuthentication(Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        return Collections.singletonMap("isAuthenticated", isAuthenticated);
    }
}
