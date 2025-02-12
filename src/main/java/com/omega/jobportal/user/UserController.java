package com.omega.jobportal.user;

import com.omega.jobportal.user.data.ForgotPasswordRequest;
import com.omega.jobportal.user.data.UpdateUserPasswordRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.verificationCode.VerificationCodeService;
import com.omega.jobportal.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final VerificationCodeService verificationCodeService;

    @GetMapping
    public PageResponse<UserResponse> findAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return userService.findAll(page, size);
    }

    @GetMapping("verify-email")
    public ResponseEntity<Void> verificationToken(@RequestParam("token") String token) {
        verificationCodeService.verifyCode(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        userService.forgotPassword(request.email());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid UpdateUserPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }
}
