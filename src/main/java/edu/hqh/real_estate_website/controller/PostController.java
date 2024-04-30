package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.*;
import edu.hqh.real_estate_website.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {
    PostService postService;

    @GetMapping("/{pageNumber}")
    String getPost(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "1") Integer pageNumber
    )
    {
        if (pageNumber == null)
            pageNumber = 1;
        var result = postService.getAllPostsPage(pageNumber);
        var posts = result.getContent();
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/post/1?outPage";
        model.addAttribute("posts", posts);
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
