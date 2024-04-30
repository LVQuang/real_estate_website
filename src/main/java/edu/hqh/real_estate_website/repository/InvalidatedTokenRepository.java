package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository <InvalidatedToken, String> { }
