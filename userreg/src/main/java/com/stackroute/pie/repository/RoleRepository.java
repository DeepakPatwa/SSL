package com.stackroute.pie.repository;

import com.stackroute.pie.domain.Role;
import com.stackroute.pie.domain.RoleName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}