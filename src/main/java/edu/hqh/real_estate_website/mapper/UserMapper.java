package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.CreateUserRequest;
import edu.hqh.real_estate_website.dto.request.UpdateUserRequest;
import edu.hqh.real_estate_website.dto.response.UserDetailResponse;
import edu.hqh.real_estate_website.dto.response.UserListResponse;
import edu.hqh.real_estate_website.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userGender", ignore = true)
    User toUserEntity(CreateUserRequest request);

    @Mapping(target = "userGender", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);

    @Mapping(target = "userGender", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDetailResponse toUserDetailResponse(User user);

    UserListResponse toUserListResponse(User user);
}
