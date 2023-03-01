package com.gnims.project.domain.user.dto;

import com.gnims.project.share.validation.ValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.gnims.project.share.message.ExceptionMessage.*;

@Getter
@NoArgsConstructor
public class PasswordDto {

    @NotBlank(message = OLD_PASSWORD_EMPTY_MESSAGE,
            groups = ValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$",
            groups = ValidationGroups.PatternCheckGroup.class,
            message = PASSWORD_ERROR_MESSAGE)
    private String oldPassword;

    @NotBlank(message = NEW_PASSWORD_EMPTY_MESSAGE,
            groups = ValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$",
            groups = ValidationGroups.PatternCheckGroup.class,
            message = PASSWORD_ERROR_MESSAGE)
    private String newPassword;
}
