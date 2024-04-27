package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.AuthenticationRequest;
import edu.hqh.real_estate_website.dto.request.RegisterRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.AuthenticationResponse;
import edu.hqh.real_estate_website.dto.response.RegisterResponse;
import edu.hqh.real_estate_website.service.AuthenticationService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationAPI {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletRequest httpRequest) {
        var result = authenticationService.authenticate(request);
        httpRequest.getSession().setAttribute("myToken", result.getToken());
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{token}")
    String authenticate(HttpServletRequest request, @PathVariable String token) throws IOException {
        request.getSession().setAttribute("myToken", token);
        return "Hello";
    }

    @PostMapping("/register")
    ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        var result = authenticationService.register(request);
        return ApiResponse.<RegisterResponse>builder()
                .result(result)
                .build();
    }
}
