package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.ContentRequest;
import edu.hqh.real_estate_website.dto.response.ContentResponse;
import edu.hqh.real_estate_website.entity.Content;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.ContentMapper;
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
public class ContentService {
    ContentRepository contentRepository;
    PostRepository postRepository;
    ContentMapper contentMapper;

    public ContentResponse getById(String id) {
        var content = contentRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return contentMapper.toResponse(content);
    }

    public void delete(String id) {
        var content = contentRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        var post = content.getPost();
        post.setContent(null);
        postRepository.save(post);
        contentRepository.deleteById(id);
    }

    public ContentResponse update(ContentRequest request, String id) {
        var content = contentRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        contentMapper.update(content, request);
        var post = content.getPost();
        post.setContent(content);
        postRepository.save(post);
        contentRepository.save(content);
        return contentMapper.toResponse(content);
    }

    public ContentResponse create(ContentRequest request) {
        var content = contentMapper.convertEntity(request);
        contentRepository.save(content);
        return contentMapper.toResponse(content);
    }
}
