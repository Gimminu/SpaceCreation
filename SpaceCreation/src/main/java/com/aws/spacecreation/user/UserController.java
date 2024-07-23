package com.aws.spacecreation.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.aws.spacecreation.user.kakao.KaKaoApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserSecuritySerivce userSecuritySerivce;
//    private final KaKaoApi kakaoApi;

//    //회원가입
//    @GetMapping("/signup")
//    public String signup(Model model) {
//        model.addAttribute("userCreateForm", new UserCreateForm());
//        model.addAttribute("kakaoApi",kakaoApi);
//        return "view/accounts/signup_form";
//    }

    //로그인
    @GetMapping("/login")
    public String login(){
        return "view/accounts/login_form";
    }


    //회원 정보 수정
    @GetMapping("/update")
    public String update(Model model)
    {
        SiteUser siteUser = userSecuritySerivce.getauthen();
        UserCreateForm userCreateForm = new UserCreateForm();
        userCreateForm.setUsername(siteUser.getUsername());
        userCreateForm.setEmail(siteUser.getEmail());
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

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "view/accounts/signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "view/accounts/signup_form";
        }
        return "redirect:/";
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

        try {
            userService.update(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
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
            userService.withdraw(userSecuritySerivce.getauthen());
        } catch (Exception e) {
            return e.getMessage();
        }
        return "redirect:/user/logout";
    }

}
