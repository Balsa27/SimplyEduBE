package com.simplyedu.AuthServer.repository;

import com.simplyedu.AuthServer.entities.Role;
import com.simplyedu.AuthServer.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
