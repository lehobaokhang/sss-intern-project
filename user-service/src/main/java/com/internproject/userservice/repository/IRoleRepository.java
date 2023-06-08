package com.internproject.userservice.repository;

import com.internproject.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);

}
