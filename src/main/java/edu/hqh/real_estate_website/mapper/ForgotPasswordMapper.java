package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.*;
import edu.hqh.real_estate_website.dto.response.ForgotPasswordResponse;
import edu.hqh.real_estate_website.dto.response.RegisterResponse;
import edu.hqh.real_estate_website.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForgotPasswordMapper {
//    User convertEntity(ForgotPasswordRequest request);
    ForgotPasswordResponse toResponse(User user);

    ForgotPasswordRequest toForgotPasswordRequest(ForgotPasswordRequest request);

    ForgotPasswordRequest toResetPasswordRequest(ResetPasswordRequest request);
}
