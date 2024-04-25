package edu.hqh.real_estate_website.repository;

import edu.hqh.real_estate_website.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    boolean existsById(String permission);
}
