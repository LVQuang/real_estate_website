package edu.hqh.real_estate_website.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    @GetMapping("/addPost")
    String getAddPost()
    {
        return "post/addPost";
    }

    @GetMapping("/addImages")
    String getAddImages()
    {
        return "post/addImages";
    }

    @GetMapping("/postDetail")
    String getPostDetail()
    {
        return "post/postDetail";
    }


}
