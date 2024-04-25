package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.RegisterRequest;
import edu.hqh.real_estate_website.dto.response.RegisterResponse;
import edu.hqh.real_estate_website.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convertEntity(RegisterRequest request);
    RegisterResponse toResponse(User user);
}
