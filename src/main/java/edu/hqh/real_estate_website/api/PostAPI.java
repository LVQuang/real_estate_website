package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.PostRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.PostDetailResponse;
import edu.hqh.real_estate_website.dto.response.PostListingResponse;
import edu.hqh.real_estate_website.entity.Post;
import edu.hqh.real_estate_website.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostAPI {
    PostService postService;

    @GetMapping("/{post}")
    ApiResponse<PostDetailResponse> getById(@PathVariable String post) {
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.getById(post))
                .build();
    }

    @GetMapping
    ApiResponse<List<PostListingResponse>> getAll() {
        return ApiResponse.<List<PostListingResponse>>builder()
                .result(postService.getAll())
                .build();
    }

    @DeleteMapping("/{post}")
    ApiResponse<Void> delete(@PathVariable String post) {
        postService.delete(post);
        return ApiResponse.<Void>builder()
                .code(2045)
                .message("Item Deleted")
                .build();
    }

    @PostMapping
    ApiResponse<PostDetailResponse> create(@RequestBody PostRequest request) {
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.create(request))
                .build();
    }

    @PutMapping("/{post}")
    ApiResponse<PostDetailResponse> update(@RequestBody PostRequest request, @PathVariable String post) {
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.update(request, post))
                .build();
    }

    @GetMapping("/page/{page}")
    ApiResponse<Page<Post>> getAllPostsPage(@PathVariable int page) {
        return ApiResponse.<Page<Post>>builder()
                .result(postService.getAllPostsPage(page))
                .build();
    }
}
