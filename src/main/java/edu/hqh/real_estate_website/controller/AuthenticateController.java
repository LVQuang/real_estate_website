package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.AuthenticationRequest;
import edu.hqh.real_estate_website.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/view/auth")
@Controller
public class AuthenticateController {

    AuthenticationService authenticationService;

    @GetMapping
    String authenticate(HttpServletRequest httpServletRequest, Model model) {
        var authenticationRequest
                = AuthenticationRequest.builder()
                .name("admin")
                .pass("123456789")
                .build();
        var token = authenticationService.authenticate(authenticationRequest).getToken();
        httpServletRequest.getSession(true).setAttribute("token", token);
        log.info((String) httpServletRequest.getSession(true).getAttribute("token"));
        return "index";
    }

//    @GetMapping("/login")
//    String showLogin() {
//        return "login";
//    }
//
//    @GetMapping("/register")
//    String showRegister() {
//        return "register";
//    }

//    @GetMapping
//    String sendEmail(HttpServletRequest httpServletRequest, Model model){
//
//    };

}
