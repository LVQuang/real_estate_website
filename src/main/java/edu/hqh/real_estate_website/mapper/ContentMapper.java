package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.ContentRequest;
import edu.hqh.real_estate_website.dto.response.ContentResponse;
import edu.hqh.real_estate_website.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    Content convertEntity(ContentRequest request);
    ContentResponse toResponse(Content content);
    void update(@MappingTarget Content content, ContentRequest request);
}
