package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.UserLoginRequest;
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
@RequestMapping("/auth")
@Controller
public class PostController {

    @GetMapping("/addPost")
    String getAddPost()
    {
        return "addPost";
    }

    @GetMapping("/addImages")
    String getAddImages()
    {
        return "addImage";
    }

    @GetMapping("/postDetail")
    String getPostDetail()
    {
        return "postDetail";
    }


}
