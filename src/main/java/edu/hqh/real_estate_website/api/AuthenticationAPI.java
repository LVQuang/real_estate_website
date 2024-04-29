package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.AuthenticationRequest;
import edu.hqh.real_estate_website.dto.request.ForgotPasswordRequest;
import edu.hqh.real_estate_website.dto.request.RegisterRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.AuthenticationResponse;
import edu.hqh.real_estate_website.dto.response.ForgotPasswordResponse;
import edu.hqh.real_estate_website.dto.response.RegisterResponse;
import edu.hqh.real_estate_website.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationAPI {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/register")
    ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        var result = authenticationService.register(request);
        return ApiResponse.<RegisterResponse>builder()
                .result(result)
                .build();
    }

    @PutMapping("/resetPassword")
    ApiResponse<ForgotPasswordResponse> reset(@RequestBody ForgotPasswordRequest request) {
        var result = authenticationService.resetPassword(request);
        return ApiResponse.<ForgotPasswordResponse>builder()
                .result(result)
                .build();
    }

}
