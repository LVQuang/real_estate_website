package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.CreateUserRequest;
import edu.hqh.real_estate_website.dto.request.UpdateUserRequest;
import edu.hqh.real_estate_website.dto.response.UserDetailResponse;
import edu.hqh.real_estate_website.dto.response.UserListResponse;
import edu.hqh.real_estate_website.entity.User;
import edu.hqh.real_estate_website.enums.UserGender;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.exception.ErrorCode;
import edu.hqh.real_estate_website.mapper.UserMapper;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTS);

        var user = userMapper.toUserEntity(request);

        user.setUserGender(UserGender
                .valueOf(request.getUserGender()));

        userRepository.save(user);
    }

    public void updateUser(String id, UpdateUserRequest request) {
        var user = getUser(id);

        userMapper.updateUser(user, request);
        user.setUserGender(UserGender.valueOf(request.getUserGender()));

        userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.delete(getUser(id));
    }

    public UserDetailResponse getDetailUser(String id) {
        var user = getUser(id);
        var response = userMapper.toUserDetailResponse(user);

        response.setUserGender(user.getUserGender().name());

        return response;
    }

    public List<UserListResponse> getUsers() {
        var users = userRepository.findAll();
        return users.stream().map(userMapper::toUserListResponse).toList();
    }

    private User getUser(String id) {
        return userRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }
}
