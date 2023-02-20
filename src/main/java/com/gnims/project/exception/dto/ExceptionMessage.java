package com.gnims.project.exception.dto;

public class ExceptionMessage {

    //User 에서 사용
    public static final String MISMATCH_EMAIL_OR_PASSWORD = "이메일 혹은 비밀번호가 일치하지 않습니다.";
    public static final String MISMATCH_NICKNAME_OR_EMAIL = "닉네임 혹은 이메일이 일치하지 않습니다.";
    public static final String ALREADY_REGISTERED_EMAIL = "이미 등록된 이메일 입니다.";
    public static final String DUPLICATE_NICKNAME = "중복된 닉네임 입니다.";
    public static final String EXTENSION_ERROR_MESSAGE = " 확장자의 이미지 파일만 업로드 가능합니다.";
    public static final String THE_SAME_PASSWORD_AS_BEFORE = "기존의 비밀번호와 같은 비밀번호 입니다.";
    public static final String CURRENT_MISMATCHED_PASSWORD = "현재 비밀번호가 일치하지 않습니다.";
    public static final String NICKNAME_ERROR_MESSAGE = "특수문자를 제외한 2 ~ 8 자리의 닉네임만 가능합니다.";
    public static final String USERNAME_ERROR_MESSAGE = "12자 이내의 한글, 영어 이름만 가능합니다.";
    public static final String EMAIL_ERROR_MESSAGE = "올바른 형식의 이메일 주소여야 합니다";
    public static final String PASSWORD_ERROR_MESSAGE = "비밀번호는 영문/숫자를 포함하여 8~16자로 입력해야합니다.";
    public static final String NICKNAME_EMPTY_MESSAGE = "닉네임은 필수 입력 값입니다.";
    public static final String USERNAME_EMPTY_MESSAGE = "이름은 필수 입력 값입니다.";
    public static final String EMAIL_EMPTY_MESSAGE = "이메일은 필수 입력 값입니다.";
    public static final String PASSWORD_EMPTY_MESSAGE = "비밀번호는 필수 입력 값입니다.";

    //Jwt 토큰
    public static final String INVALID_TOKEN_ERROR = "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.";
    public static final String EXPIRED_TOKEN_ERROR = "Expired JWT token, 만료된 JWT token 입니다.";
    public static final String UNSUPPORTED_TOKEN_ERROR = "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.";
    public static final String WRONG_TOKEN_ERROR = "JWT claims is empty, 잘못된 JWT 토큰 입니다.";
    public static final String TOKEN_ERROR = "Token Error";
}
