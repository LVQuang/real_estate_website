package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.Post;
import edu.hqh.real_estate_website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    boolean existsById(String id);
    List<Post> findByUser(User user);
}
