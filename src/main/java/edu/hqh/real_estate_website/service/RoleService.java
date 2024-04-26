package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.RoleRequest;
import edu.hqh.real_estate_website.dto.response.RoleResponse;
import edu.hqh.real_estate_website.mapper.RoleMapper;
import edu.hqh.real_estate_website.repository.PermissionRepository;
import edu.hqh.real_estate_website.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.convertEntity(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    public void delete(String role) { roleRepository.deleteById(role); }
}
