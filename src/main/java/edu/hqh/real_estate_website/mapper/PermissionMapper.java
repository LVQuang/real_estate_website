package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.PermissionRequest;
import edu.hqh.real_estate_website.dto.response.PermissionResponse;
import edu.hqh.real_estate_website.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission convertEntity(PermissionRequest request);
    PermissionResponse toResponse(Permission permission);
}
