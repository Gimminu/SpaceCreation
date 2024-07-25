package com.aws.spacecreation.user.auth;

import lombok.Getter;

@Getter
public enum UserProvider {
    FACEBOOK("User_FACEBOOK"),
    GOOGLE("User_GOOGLE"),
    NAVER("User_NAVER"),
    KAKAO("User_KAKAO"),
    COMMON("User_COMMON");

    private String value;

    UserProvider(String value){
        this.value = value;
    }

}

