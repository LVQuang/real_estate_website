package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.RegisterRequest;
import edu.hqh.real_estate_website.dto.request.UserRegisterRequest;
import edu.hqh.real_estate_website.dto.response.RegisterResponse;
import edu.hqh.real_estate_website.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    @Mapping(target = "gender", ignore = true)
    User convertEntity(RegisterRequest request);
    RegisterResponse toResponse(User user);
    RegisterRequest toRegisterRequest(UserRegisterRequest request);
}
