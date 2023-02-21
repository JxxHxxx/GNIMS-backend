package com.gnims.project.util.gmail;

import com.gnims.project.domain.user.dto.SimpleMessageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.gnims.project.util.ResponseMessage.PASSWORD_UPDATE_SUCCESS_MESSAGE;
import static com.gnims.project.util.ResponseMessage.SUCCESS_AUTH_EMAIL_MESSAGE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailServiceImpl emailServiceImpl;

    @PatchMapping("/email/password")
    public ResponseEntity<SimpleMessageResult> updatePassword(@RequestBody EmailPasswordDto request)  throws Exception {

        emailServiceImpl.updatePassword(request);

        return new ResponseEntity<>(new SimpleMessageResult(OK.value(), PASSWORD_UPDATE_SUCCESS_MESSAGE), OK);
    }

    @PatchMapping("/auth/code")
    public ResponseEntity<SimpleMessageResult> updatePassword(@RequestBody AuthCodeDto request)  throws Exception {

        emailServiceImpl.checkCode(request);

        return new ResponseEntity<>(new SimpleMessageResult(OK.value(), SUCCESS_AUTH_EMAIL_MESSAGE), OK);
    }
}
