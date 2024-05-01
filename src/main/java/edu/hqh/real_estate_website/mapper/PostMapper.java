package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.AddPostRequest;
import edu.hqh.real_estate_website.dto.request.PostRequest;
import edu.hqh.real_estate_website.dto.response.PostDetailResponse;
import edu.hqh.real_estate_website.dto.response.PostListingResponse;
import edu.hqh.real_estate_website.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "transactions", ignore = true)
    Post convertEntity(PostRequest request);
    PostDetailResponse toResponse(Post post);
    PostListingResponse toListResponse(Post post);
    @Mapping(target = "transactions", ignore = true)
    void update(@MappingTarget Post post, PostRequest request);
    PostRequest toAddPostRequest(AddPostRequest request);
}
