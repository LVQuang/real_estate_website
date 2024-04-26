package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.ContactRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.ContactResponse;
import edu.hqh.real_estate_website.service.ContactService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactAPI {
    ContactService contactService;

    @GetMapping
    ApiResponse<List<ContactResponse>> getAll() {
        return ApiResponse.<List<ContactResponse>>builder()
                .result(contactService.getAll())
                .build();
    }

    @PostMapping("/{receiveId}")
    ApiResponse<ContactResponse> create(@RequestBody ContactRequest request, @PathVariable String receiveId) {
        return ApiResponse.<ContactResponse>builder()
                .result(contactService.create(request, receiveId))
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        contactService.delete(permission);
        return ApiResponse.<Void>builder()
                .code(2045)
                .message("Item Deleted")
                .build();
    }
}
