package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.ContactRequest;
import edu.hqh.real_estate_website.dto.response.ContactResponse;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.ContactMapper;
import edu.hqh.real_estate_website.repository.ContactRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ContactService {
    ContactRepository contactRepository;
    UserRepository userRepository;
    ContactMapper contactMapper;

    public ContactResponse create(ContactRequest request, String receiverId) {
        var contact = contactMapper.convertEntity(request);
        var senderName = SecurityContextHolder.getContext().getAuthentication().getName();
        var sender = userRepository.findByName(senderName).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var receiver = userRepository.findById(receiverId).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        contact.setContactDate(LocalDate.now());
        contact.setSender(sender.getId());
        contact.setReceiver(receiverId);

        contactRepository.save(contact);

        sender.getContacts().add(contact);
        receiver.getContacts().add(contact);

        userRepository.saveAll(List.of(sender, receiver));

        return contactMapper.toResponse(contact);
    }

    public List<ContactResponse> getAll() {
        var contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contactMapper::toResponse)
                .toList();
    }

    public void delete(String id) {
        var contact = contactRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        var sender = userRepository.findById(contact.getSender()).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        var receiver = userRepository.findById(contact.getReceiver()).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        sender.getContacts().remove(contact);
        receiver.getContacts().remove(contact);

        userRepository.saveAll(List.of(sender, receiver));

        contactRepository.deleteById(id);
    }
}
