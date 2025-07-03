package com.omega.jobportal.user;

import com.omega.jobportal.user.data.CompleteRegistrationRequest;
import com.omega.jobportal.user.data.UpdateUserPasswordRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.data.UserUpdateRequest;
import com.omega.jobportal.user.userConnectedAccount.UserConnectedAccount;
import com.omega.jobportal.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public PageResponse<UserResponse> findAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return userService.findAll(page, size);
    }

    @GetMapping("/user-connected-accounts")
    public List<UserConnectedAccount> findUserConnectedAccounts() {
        return userService.findUserConnectedAccounts();
    }

    @PatchMapping("/onboarding")
    public UserResponse completeRegistration(@Valid @RequestBody CompleteRegistrationRequest request) {
        return userService.completeUserRegistration(request);
    }

    @PatchMapping("/update-password")
    public void updatePassword(@Valid @RequestBody UpdateUserPasswordRequest request) {
        userService.updatePassword(request);
    }

    @PatchMapping("/update-profile")
    public UserResponse updateProfile(@Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(request);
    }
}
