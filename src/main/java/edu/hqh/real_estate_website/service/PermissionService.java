package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.PermissionRequest;
import edu.hqh.real_estate_website.dto.response.PermissionResponse;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
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
        if(permissionRepository.existsById(request.getName())) {
            throw new AppException(ErrorCode.ITEM_EXISTS);
        }
        var permission = permissionMapper.convertEntity(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    public void delete(String permission) {
        if(!permissionRepository.existsById(permission))
            throw new AppException(ErrorCode.ITEM_DONT_EXISTS);
        permissionRepository.deleteById(permission);
    }
}
