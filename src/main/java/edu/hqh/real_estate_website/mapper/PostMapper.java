package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.ContentRequest;
import edu.hqh.real_estate_website.dto.request.PostUpdateRequest;
import edu.hqh.real_estate_website.dto.response.PostResponse;
import edu.hqh.real_estate_website.entity.Content;
import edu.hqh.real_estate_website.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
//    Post convertEntity(Post request);
    PostResponse toResponse(Post post);
    void update(@MappingTarget Post post, PostUpdateRequest request);
}
