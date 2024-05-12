package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByName(String name);
    Optional<User> findByName(String name);

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}