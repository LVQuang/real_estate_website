package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.*;
import edu.hqh.real_estate_website.mapper.AuthenticationMapper;
import edu.hqh.real_estate_website.mapper.ForgotPasswordMapper;
import edu.hqh.real_estate_website.mapper.RegisterMapper;
import edu.hqh.real_estate_website.repository.UserRepository;
import edu.hqh.real_estate_website.service.AuthenticationService;
import edu.hqh.real_estate_website.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import edu.hqh.real_estate_website.dto.request.UserLoginRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    @GetMapping
    String getLogin(Model model, @ModelAttribute("page") PageNavRequest page)
    {
        log.info(page.getSearchKey());
        page = new PageNavRequest();
        model.addAttribute("page", page);
        return "index";
    }


    @GetMapping("/addPost")
    String getAddPost()
    {
        return "addPost";
    }

    @GetMapping("/addImages")
    String getAddImages()
    {
        return "addImages";
    }

    @GetMapping("/postDetail")
    String getPostDetail()
    {
        return "postDetail";
    }


}
