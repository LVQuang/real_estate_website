package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.ContactRequest;
import edu.hqh.real_estate_website.dto.response.ContactResponse;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.ContactMapper;
import edu.hqh.real_estate_website.repository.ContactRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ContactService {
    ContactRepository contactRepository;
    ContactMapper contactMapper;

    public ContactResponse create(ContactRequest request) {
        var contact = contactMapper.convertEntity(request);
        return contactMapper.toResponse(contactRepository.save(contact));
    }

    public List<ContactResponse> getAll() {
        var contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contactMapper::toResponse)
                .toList();
    }

    public void delete(String contact) {
        if(!contactRepository.existsById(contact))
            throw new AppException(ErrorCode.ITEM_DONT_EXISTS);
        contactRepository.deleteById(contact);
    }
}
