package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.*;
import edu.hqh.real_estate_website.dto.response.PostDetailResponse;
import edu.hqh.real_estate_website.entity.Image;
import edu.hqh.real_estate_website.entity.Post;
import edu.hqh.real_estate_website.mapper.PostMapper;
import edu.hqh.real_estate_website.service.ImageService;
import edu.hqh.real_estate_website.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {
    PostService postService;
    PostMapper postMapper;

    ImageService imageService;

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
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "index";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/post/1?outPage";
        return "index";
    }


    @GetMapping("/addPost")
    String getAddPost(Model model)
    {
        UserAddPostRequest user = new UserAddPostRequest();
        model.addAttribute("user", user);
        return "user/addPost";
    }

    @PostMapping("/addPost")
    String postAddPost(@Valid @ModelAttribute("user") UserAddPostRequest user, RedirectAttributes redirectAttributes){
        user.setPostDate(LocalDate.now().toString());
        user.setId(UUID.randomUUID().toString());
        var request = postMapper.toAddPostRequest(user);
        var post = postService.create(request);
        redirectAttributes.addAttribute("post_id", post.getId());
        return "redirect:/post/addImages";
    }

    @GetMapping("/addImages")
    String getAddImages(Model model, @RequestParam("post_id") String postId )
    {
        model.addAttribute("post_id", postId);
        return "user/addImages";
    }

    @PostMapping("/addImages")
    String postAddImages(@RequestParam("post_id") String postId,
                         @RequestParam("image") List<MultipartFile> files)  throws IOException, SQLException {
        for (MultipartFile file : files) {
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

            Image image = new Image();
            image.setContent(blob);
            image.setImageDate(LocalDate.now());

            PostDetailResponse postRep = postService.getById(postId);
            postMapper.

            Post post = new Post();
            post.setId(postRep.getId());
            image.setPost(post);

            imageService.create(image);
        }
        return "redirect:/post/postList";
    }

    @GetMapping("/posts")
    String getPostList(Model model)
    {
        return "listing/posts";
    }

    @GetMapping("/postDetail")
    String getPostDetail()
    {
        return "user/postDetail";
    }
    
}
