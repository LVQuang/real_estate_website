package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.PermissionRequest;
import edu.hqh.real_estate_website.dto.response.PermissionResponse;
import edu.hqh.real_estate_website.mapper.PermissionMapper;
import edu.hqh.real_estate_website.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor  @Slf4j
@Service
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        var permission = permissionMapper.createPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions
                .stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
