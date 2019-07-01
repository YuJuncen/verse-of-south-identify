package edu.csust.demo.identify.domain.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
    Role findFirstByRoleName(String roleName);
}
