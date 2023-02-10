package com.gnims.project.social.dto;

import com.gnims.project.domain.user.entity.SocialCode;
import com.gnims.project.util.validation.ValidationGroups;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SocialSignupDto {
    @NotBlank(message = "닉네임은 필수 입력 값입니다.",
            groups = ValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$",
            groups = ValidationGroups.PatternCheckGroup.class,
            message = "특수문자를 제외한 2 ~ 8 자리의 닉네임만 가능합니다.")
    private String nickname;

    @NotBlank(message = "이름은 필수 입력 값입니다.",
            groups = ValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,8}$",
            groups = ValidationGroups.PatternCheckGroup.class,
            message = "한글, 영어 이름만 가능합니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다.",
            groups = ValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            groups = ValidationGroups.PatternCheckGroup.class,
            message = "올바른 형식의 이메일 주소여야 합니다")
    private String email;

    private SocialCode socialCode;
}
