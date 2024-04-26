package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.ContentRequest;
import edu.hqh.real_estate_website.dto.request.PostUpdateRequest;
import edu.hqh.real_estate_website.dto.response.ContentResponse;
import edu.hqh.real_estate_website.dto.response.PostResponse;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.ContentMapper;
import edu.hqh.real_estate_website.mapper.PostMapper;
import edu.hqh.real_estate_website.repository.ContentRepository;
import edu.hqh.real_estate_website.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class PostService {
    PostRepository postRepository;
    PostMapper postMapper;

    public PostResponse getById(String id) {
        var post = postRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return postMapper.toResponse(post);
    }

    public void delete(String id) {
        var post = postRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        postRepository.deleteById(id);
    }

//    public PostResponse update(PostUpdateRequest request, String id) {
//        var post = postRepository.findById(id).orElseThrow(()
//                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
//        postMapper.update(post, request);
//        return contentMapper.toResponse(content);
//    }
//
//    public ContentResponse create(ContentRequest request) {
//        var content = contentMapper.convertEntity(request);
//        contentRepository.save(content);
//        return contentMapper.toResponse(content);
//    }
}
