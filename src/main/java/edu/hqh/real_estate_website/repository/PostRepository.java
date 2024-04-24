package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> { }
