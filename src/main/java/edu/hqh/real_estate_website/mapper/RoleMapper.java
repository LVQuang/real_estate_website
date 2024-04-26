package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.RoleRequest;
import edu.hqh.real_estate_website.dto.response.RoleResponse;
import edu.hqh.real_estate_website.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role convertEntity(RoleRequest request);
    RoleResponse toResponse(Role role);
}
