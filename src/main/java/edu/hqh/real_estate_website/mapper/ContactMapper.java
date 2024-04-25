package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.ContactRequest;
import edu.hqh.real_estate_website.dto.response.ContactResponse;
import edu.hqh.real_estate_website.entity.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact convertEntity(ContactRequest request);
    ContactResponse toResponse(Contact contact);
}
