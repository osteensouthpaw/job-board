package com.omega.jobportal.user;

import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.verificationCode.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final VerificationCodeService service;

    @GetMapping
    public List<UserResponse> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("verify-email")
    public ResponseEntity<Void> verificationToken(@RequestParam("token") String token) {
        service.verifyCode(token);
        return ResponseEntity.noContent().build();
    }
}
