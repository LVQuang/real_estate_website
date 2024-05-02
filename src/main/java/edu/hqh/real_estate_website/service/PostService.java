package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.PostRequest;
import edu.hqh.real_estate_website.dto.response.PostDetailResponse;
import edu.hqh.real_estate_website.dto.response.PostListingResponse;
import edu.hqh.real_estate_website.entity.Image;
import edu.hqh.real_estate_website.entity.Post;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.enums.PostState;
import edu.hqh.real_estate_website.enums.TypePost;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.PostMapper;
import edu.hqh.real_estate_website.repository.ImageRepository;
import edu.hqh.real_estate_website.repository.PostRepository;
import edu.hqh.real_estate_website.repository.TransactionRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class PostService {
    ImageRepository imageRepository;
    PostRepository postRepository;
    UserRepository userRepository;
    TransactionRepository transactionRepository;
    UserService userService;
    PostMapper postMapper;

    public PostDetailResponse getById(String id) {
        var post = postRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        log.info(post.getId());

        return postMapper.toResponse(post);
    }

    public List<PostListingResponse> getAll() {
        var posts = postRepository.findAll();
        return posts
                .stream()
                .map(postMapper::toListResponse)
                .toList();
    }

    public List<Post> getMyPosts() {
        var user = userService.getCurrentUser();
        return postRepository.findByUser(user);
    }

    public void delete(String id) {
        var post = postRepository
                .findById(id).orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var transactions = post.getTransactions();
        transactions.forEach(transaction -> transaction.setPost(null));
        transactionRepository.saveAll(transactions);

        var images = post.getImages();
        images.forEach(image -> image.setPost(null));
        imageRepository.saveAll(images);

        var user = post.getUser();
        user.getPosts().remove(post);
        userRepository.save(user);


        post.setImages(new HashSet<>());
        post.setTransactions(new HashSet<>());
        post.setUser(null);
        postRepository.save(post);

        postRepository.deleteById(id);
    }


    public PostDetailResponse create(PostRequest request) {
        var post = postMapper.convertEntity(request);
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByName(userName).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var userPost = user.getPosts();
        userPost.add(post);

        post.setUser(user);
        user.setPosts(userPost);

        post.setPostDate(LocalDate.now());
        post.setAvailable(PostState.YES);
        post.setTitle(request.getTitle());
        post.setAddress(request.getAddress());
        post.setPrice(request.getPrice());
        post.setDescription(request.getDescription());
        post.setType(TypePost.valueOf(request.getType()));

        userRepository.save(user);

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
        var result = postRepository.findAll();
        return getAllPostsPageImpl(page, result);
    }

    public Page<Post> getAllMyPostPage(int page) {
        var user = userService.getCurrentUser();
        var result = postRepository.findByUser(user);
        return getAllPostsPageImpl(page, result);
    }

    private Page<Post> getAllPostsPageImpl(int page, List<Post> result) {
        int pageSize = 4;

        if(result.size() < pageSize)
            pageSize = result.size() ;

        Pageable pageable = PageRequest.of(page, pageSize);

        int start =(int) pageable.getOffset();
        int end = Math.min( (start + pageable.getPageSize()) , result.size());

        var content = result.subList(start, end);
        return new PageImpl<>(content, pageable, result.size());
    }

    public List<Image> getImagesByPostId(String postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return new ArrayList<Image>(post.getImages());
    }

}
