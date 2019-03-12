package com.simple.simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple.simple.entity.RoleEntity;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    
    RoleEntity findByRole(String role);
}
