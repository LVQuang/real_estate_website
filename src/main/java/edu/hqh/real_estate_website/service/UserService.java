package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.CreateUserRequest;
import edu.hqh.real_estate_website.dto.request.UpdateUserRequest;
import edu.hqh.real_estate_website.dto.response.UserDetailResponse;
import edu.hqh.real_estate_website.dto.response.UserListResponse;
import edu.hqh.real_estate_website.entity.Role;
import edu.hqh.real_estate_website.enums.UserGender;
import edu.hqh.real_estate_website.enums.UserRole;
import edu.hqh.real_estate_website.mapper.UserMapper;
import edu.hqh.real_estate_website.repository.RoleRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    public void createUser(CreateUserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        var user = userMapper.toUserEntity(request);
        var userRole = roleRepository.findById(UserRole.USER.name()).orElseThrow();

        user.setRoles(Collections.singleton(userRole));
        user.setUserGender(UserGender.valueOf(request.getUserGender()));

        userRepository.save(user);
    }

    public void updateUser(String id, UpdateUserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        var user = userRepository.findById(id).orElseThrow();

        userMapper.updateUser(user, request);
        user.setUserGender(UserGender.valueOf(request.getUserGender()));

        userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserDetailResponse getUser(String id) {
        var user = userRepository.findById(id).orElseThrow();

        var response = userMapper.toUserDetailResponse(user);

        response.setUserGender(user.getUserGender().name());
        response.setRoles(user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));

        return response;
    }

    public List<UserListResponse> getUsers() {
        var users = userRepository.findAll();
        return users.stream().map(userMapper::toUserListResponse).toList();
    }
}
