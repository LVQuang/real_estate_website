package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
    boolean existsById(String contact);
    List<Contact> findBySender(String sender);
}
