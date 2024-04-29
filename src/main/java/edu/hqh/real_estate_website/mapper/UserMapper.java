package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.UserRequest;
import edu.hqh.real_estate_website.dto.response.UserResponse;
import edu.hqh.real_estate_website.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "posts", ignore = true)
    void update(@MappingTarget User user, UserRequest request);

}
