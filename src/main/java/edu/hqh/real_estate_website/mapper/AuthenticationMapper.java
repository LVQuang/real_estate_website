package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.AuthenticationRequest;
import edu.hqh.real_estate_website.dto.request.UserLoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
    AuthenticationRequest toAuthenticationRequest(UserLoginRequest request);
}
