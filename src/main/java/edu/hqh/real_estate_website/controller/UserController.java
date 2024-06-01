package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.CreateUserRequest;
import edu.hqh.real_estate_website.dto.request.UpdateUserRequest;
import edu.hqh.real_estate_website.dto.response.UserDetailResponse;
import edu.hqh.real_estate_website.dto.response.UserListResponse;
import edu.hqh.real_estate_website.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    void createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
    }

    @PutMapping("/{userId}")
    void updateUser(@RequestBody @Valid UpdateUserRequest request, @PathVariable("userId") String id) {
        userService.updateUser(id, request);
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable("userId") String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{userId}")
    UserDetailResponse getUser(@PathVariable("userId") String id) { return userService.getDetailUser(id); }

    @GetMapping
    List<UserListResponse> getUsers() {
        return userService.getUsers();
    }
}
