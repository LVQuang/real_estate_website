package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
public class AuthenticateController {

    AuthenticationService authenticationService;

    @GetMapping("/login")
    String login(HttpServletResponse response)
    {

        return "login";
    }
}
