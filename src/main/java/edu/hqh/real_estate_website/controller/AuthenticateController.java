package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.UserLoginRequest;
import edu.hqh.real_estate_website.mapper.AuthenticationMapper;
import edu.hqh.real_estate_website.service.AuthenticationService;
import edu.hqh.real_estate_website.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@Controller
public class AuthenticateController {

    AuthenticationService authenticationService;
    AuthenticationMapper authenticationMapper;

    @GetMapping("/login")
    String login(Model model)
    {
        UserLoginRequest user = new UserLoginRequest();
        model.addAttribute("user", user);
        return "login";
    }
    @PostMapping("/login")
    String test(@Valid @ModelAttribute("user") UserLoginRequest user
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
}
