package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.UserLoginRequest;
import edu.hqh.real_estate_website.dto.request.UserRegisterRequest;
import edu.hqh.real_estate_website.enums.UserGender;
import edu.hqh.real_estate_website.mapper.AuthenticationMapper;
import edu.hqh.real_estate_website.mapper.RegisterMapper;
import edu.hqh.real_estate_website.repository.UserRepository;
import edu.hqh.real_estate_website.service.AuthenticationService;
import edu.hqh.real_estate_website.service.UserService;
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
    RegisterMapper registerMapper;

    @GetMapping("/login")
    String getLogin(Model model)
    {
        UserLoginRequest user = new UserLoginRequest();
        model.addAttribute("user", user);
        return "login";
    }
    @PostMapping("/login")
    String postLogin(@Valid @ModelAttribute("user") UserLoginRequest user
            , HttpServletRequest httpRequest)
    {
        var authRequest = authenticationMapper.toAuthenticationRequest(user);
        var authentication = authenticationService.authenticate(authRequest, true);
        log.info(String.valueOf(authentication.isAuthenticated()));
        if(!authentication.isAuthenticated())
            return "redirect:/login?incorrect";
        else
        {
            httpRequest.getSession().setAttribute("myToken", authentication.getToken());
            return "index";
        }
    }

    @GetMapping("/register")
    String getRegister(Model model) {
        UserRegisterRequest user = new UserRegisterRequest();
        model.addAttribute("user", user);
        return "register";
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
}
