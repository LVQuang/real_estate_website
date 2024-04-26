package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.UserRequest;
import edu.hqh.real_estate_website.dto.response.UserResponse;
import edu.hqh.real_estate_website.entity.User;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.UserMapper;
import edu.hqh.real_estate_website.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    ContactRepository contactRepository;
    TransactionRepository transactionRepository;
    PostRepository postRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    public List<UserResponse> getAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getById(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return userMapper.toResponse(user);
    }

    public UserResponse getMyInfo() {
        var user = getCurrentUser();
        return userMapper.toResponse(user);
    }

    public UserResponse update(UserRequest request) {
        var user = getCurrentUser();
        userMapper.update(user, request);

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        var contacts = contactRepository.findAllById(request.getContacts());
        user.setContacts(new HashSet<>(contacts));

        var transactions = transactionRepository.findAllById(request.getTransactions());
        user.setTransactions(new HashSet<>(transactions));

        var posts = postRepository.findAllById(request.getPosts());
        user.setPosts(new HashSet<>(posts));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    private User getCurrentUser() {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
    }
}
