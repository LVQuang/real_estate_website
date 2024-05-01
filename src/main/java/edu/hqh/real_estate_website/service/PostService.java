package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.PostRequest;
import edu.hqh.real_estate_website.dto.response.PostDetailResponse;
import edu.hqh.real_estate_website.dto.response.PostListingResponse;
import edu.hqh.real_estate_website.entity.Post;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.enums.PostState;
import edu.hqh.real_estate_website.enums.TypePost;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.PostMapper;
import edu.hqh.real_estate_website.repository.PostRepository;
import edu.hqh.real_estate_website.repository.TransactionRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    TransactionRepository transactionRepository;
    PostMapper postMapper;

    public PostDetailResponse getById(String id) {
        var post = postRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return postMapper.toResponse(post);
    }

    public List<PostListingResponse> getAll() {
        var posts = postRepository.findAll();
        return posts
                .stream()
                .map(postMapper::toListResponse)
                .toList();
    }

    public void delete(String id) {
        if(!postRepository.existsById(id))
            throw new AppException(ErrorCode.ITEM_DONT_EXISTS);
        postRepository.deleteById(id);
    }


    public PostDetailResponse create(PostRequest request) {
        var post = postMapper.convertEntity(request);
//        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        var user = userRepository.findByName(userName).orElseThrow(()
//                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
//        var userPost = user.getPosts();
//        userPost.add(post);
//
//        post.setUser(user);
//        user.setPosts(userPost);

        post.setPostDate(LocalDate.now());
        post.setAvailable(PostState.YES);
        post.setTitle(request.getTitle());
        post.setAddress(request.getAddress());
        post.setPrice(request.getPrice());
        post.setDescription(request.getDescription());
        post.setType(TypePost.valueOf(request.getType()));

//        userRepository.save(user);
        return postMapper.toResponse(postRepository.save(post));
    }

    public PostDetailResponse update(PostRequest request, String id) {
        var post = postRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS) );

        postMapper.update(post, request);
        if(request.getTransactions() != null) {
            var transactions = transactionRepository
                    .findAllById(request.getTransactions());
            post.setTransactions(new HashSet<>(transactions));
        }
        post.setAvailable(PostState.valueOf(request.getAvailable()));

        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    public Page<Post> getAllPostsPage(int page) {
        Pageable pageable = PageRequest.of(page, 4);
        return postRepository.findAll(pageable);
    }
}
