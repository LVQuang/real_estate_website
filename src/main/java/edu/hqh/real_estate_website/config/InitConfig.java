package edu.hqh.real_estate_website.config;

import edu.hqh.real_estate_website.entity.Permission;
import edu.hqh.real_estate_website.entity.Role;
import edu.hqh.real_estate_website.entity.User;
import edu.hqh.real_estate_website.enums.PermissionName;
import edu.hqh.real_estate_website.enums.RoleName;
import edu.hqh.real_estate_website.repository.PermissionRepository;
import edu.hqh.real_estate_website.repository.RoleRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@Slf4j
public class InitConfig {
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;


    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            initPermission();
            initRole();
            initAdmin();
        };
    }

    private void initPermission() {
        if(permissionRepository.findAll().isEmpty()) {
            var permissions
                    = List.of(PermissionName.G_POST.getName()
                    , PermissionName.GA_POSTS.getName());
            permissions.forEach(permission -> permissionRepository
                    .save(Permission.builder()
                            .name(permission)
                            .build()));
        }
    }

    private void initRole() {
        if(roleRepository.findAll().isEmpty()) {
            var roles
                    = List.of(RoleName.ADMIN.getName(), RoleName.USER.getName());
            var permissions = new HashSet<>(permissionRepository.findAll());
            roles.forEach(role -> roleRepository
                    .save(Role.builder()
                            .name(role)
                            .permissions(permissions)
                            .build()));
        }
    }

    private void initAdmin() {
        if(userRepository.findAll().isEmpty()) {
            var role = roleRepository.findById(RoleName.ADMIN.getName()).orElse(null);
            userRepository
                    .save(User.builder()
                            .name("admin")
                            .password(passwordEncoder.encode("123456789"))
                            .roles(Collections.singleton(role))
                            .build());
        }
    }

}
