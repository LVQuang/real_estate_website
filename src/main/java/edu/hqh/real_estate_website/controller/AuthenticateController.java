package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.UserForgotPasswordRequest;
import edu.hqh.real_estate_website.dto.request.UserLoginRequest;
import edu.hqh.real_estate_website.dto.request.UserRegisterRequest;
import edu.hqh.real_estate_website.mapper.AuthenticationMapper;
import edu.hqh.real_estate_website.dto.request.UserResetPasswordRequest;
import edu.hqh.real_estate_website.mapper.ForgotPasswordMapper;
import edu.hqh.real_estate_website.mapper.RegisterMapper;
import edu.hqh.real_estate_website.repository.UserRepository;
import edu.hqh.real_estate_website.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthenticateController {
    private final UserRepository userRepository;

    AuthenticationService authenticationService;
    AuthenticationMapper authenticationMapper;
    ForgotPasswordMapper forgotPasswordMapper;
    RegisterMapper registerMapper;

    @GetMapping("/login")
    String getLogin(Model model)
    {
        UserLoginRequest user = new UserLoginRequest();
        model.addAttribute("user", user);
        return "user/login";
    }

    @PostMapping("/login")
    String postLogin(@Valid @ModelAttribute("user") UserLoginRequest user
            , HttpServletRequest httpRequest)
    {
        var authRequest = authenticationMapper.toAuthenticationRequest(user);
        var authentication = authenticationService.authenticate(authRequest, true);
        log.info(String.valueOf(authentication.isAuthenticated()));
        if(!authentication.isAuthenticated())
            return "redirect:/auth/login?incorrect";
        else
        {
            httpRequest.getSession().setAttribute("myToken", authentication.getToken());
            return "redirect:/post/1";
        }
    }

    @GetMapping("/register")
    String getRegister(Model model) {
        UserRegisterRequest user = new UserRegisterRequest();
        model.addAttribute("user", user);
        return "user/register";
    }

    @PostMapping("/register")
    String postRegister(@Valid @ModelAttribute("user") UserRegisterRequest user)
    {
        var request = registerMapper.toRegisterRequest(user);
        if(userRepository.existsByName(user.getName()))
            return "redirect:/auth/register?user_exists_name";
        authenticationService.register(request);
        return "redirect:/auth/login?register_success";
    }

    @GetMapping("/test")
    String test() {
        return "index";
    }

    @GetMapping("/ForgotPassword")
    String getForgot(Model model){
        UserForgotPasswordRequest user = new UserForgotPasswordRequest();
        model.addAttribute("user", user);
        return "password/forgotPassword";
    }

    @PostMapping("/ForgotPassword")
    String postForgot(@Valid @ModelAttribute("user") UserForgotPasswordRequest user){
        var authRequest = forgotPasswordMapper.toForgotPasswordRequest(user);
        if (!userRepository.existsByEmail(user.getEmail()))
            return "redirect:/auth/ForgotPassword?email_not_exists";
        authenticationService.forgotPassword(authRequest);
        return "redirect:/auth/ForgotPassword?check_email";
    }

    @GetMapping("/ResetPassword")
    String getReset(Model model){
        UserResetPasswordRequest user = new UserResetPasswordRequest();
        model.addAttribute("user", user);
        return "password/resetPassword";
    }

    @PostMapping("/ResetPassword")
    String postReset(@Valid @ModelAttribute("user") UserResetPasswordRequest user){
        var authRequest = forgotPasswordMapper.toResetPasswordRequest(user);
        if (!userRepository.existsByEmail(user.getEmail()))
            return "redirect:/auth/ResetPassword?email_not_exist";
        authenticationService.resetPassword(authRequest);
        return "redirect:/auth/login?resetPass_success";
    }
}
