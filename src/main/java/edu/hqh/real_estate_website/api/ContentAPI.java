package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.ContentRequest;
import edu.hqh.real_estate_website.dto.request.PermissionRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.ContentResponse;
import edu.hqh.real_estate_website.dto.response.PermissionResponse;
import edu.hqh.real_estate_website.service.ContentService;
import edu.hqh.real_estate_website.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContentAPI {
    ContentService contentService;

    @PostMapping
    ApiResponse<ContentResponse> create(@RequestBody ContentRequest request) {
        return ApiResponse.<ContentResponse>builder()
                .result(contentService.create(request))
                .build();
    }

    @GetMapping("/{content}")
    ApiResponse<ContentResponse> getById(@PathVariable String content) {
        return ApiResponse.<ContentResponse>builder()
                .result(contentService.getById(content))
                .build();
    }

    @DeleteMapping("/{content}")
    ApiResponse<Void> delete(@PathVariable String content) {
        contentService.delete(content);
        return ApiResponse.<Void>builder()
                .code(2045)
                .message("Item Deleted")
                .build();
    }
}
