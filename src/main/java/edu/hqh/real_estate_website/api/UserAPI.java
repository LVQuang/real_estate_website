package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.UserRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.UserResponse;
import edu.hqh.real_estate_website.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAPI {
    UserService userService;

    @GetMapping
    ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/{user}")
    ApiResponse<UserResponse> getById(@PathVariable String user) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getById(user))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping
    ApiResponse<UserResponse> updateMyInfo(@RequestBody UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.update(request))
                .build();
    }
}
