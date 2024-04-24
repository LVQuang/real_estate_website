package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> { }
