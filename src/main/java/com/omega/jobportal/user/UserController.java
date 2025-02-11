package com.omega.jobportal.user;

import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.verificationCode.VerificationCodeService;
import com.omega.jobportal.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final VerificationCodeService service;

    @GetMapping
    public PageResponse<UserResponse> findAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return userService.findAll(page, size);
    }

    @GetMapping("verify-email")
    public ResponseEntity<Void> verificationToken(@RequestParam("token") String token) {
        service.verifyCode(token);
        return ResponseEntity.noContent().build();
    }
}
