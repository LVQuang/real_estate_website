package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, String> { }
