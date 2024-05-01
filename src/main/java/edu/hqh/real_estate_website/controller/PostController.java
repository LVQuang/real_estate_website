package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.*;
import edu.hqh.real_estate_website.dto.response.PostDetailResponse;
import edu.hqh.real_estate_website.entity.Image;
import edu.hqh.real_estate_website.mapper.PostMapper;
import edu.hqh.real_estate_website.service.ImageService;
import edu.hqh.real_estate_website.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        var result = postService.getAllPostsPage(pageNumber);
        var posts = result.getContent();
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages",
                result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "index";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/post/null?page=0&outPage=true";
        return "index";
    }


    @GetMapping("/addPost")
    String getAddPost(Model model)
    {
        AddPostRequest user = new AddPostRequest();
        model.addAttribute("user", user);
        return "/addPost";
    }

    @PostMapping("/addPost")
    String postAddPost(@Valid @ModelAttribute("user") AddPostRequest user, RedirectAttributes redirectAttributes){
        var request = postMapper.toAddPostRequest(user);
        var post = postService.create(request);

        redirectAttributes.addAttribute("post_id", post.getId());

        return "redirect:/post/addImages";
    }

    @GetMapping("/addImages")
    String getAddImages(Model model, @RequestParam("post_id") String postId )
    {
        model.addAttribute("post_id", postId);
        return "/addImages";
    }

    @PostMapping("/addImages")
    String postAddImages(@RequestParam("post_id") String postId,
                         @RequestParam("image") List<MultipartFile> files)  throws IOException, SQLException {

        if (imageService.createListImage(files, postId))
            return "redirect:/post/1";
        return "redirect:/post/1?outPage";
    }


    @GetMapping("/postDetail")
    String getPostDetail(Model model, @RequestParam("id") String postId)
    {
        PostDetailResponse postDetailResponse = postService.getById(postId);
        List<Image> images = postService.getImagesByPostId(postId);
        model.addAttribute("postDetailRep", postDetailResponse);
        model.addAttribute("images", images);

        return "/layout/postDetail";
    }
    
}
