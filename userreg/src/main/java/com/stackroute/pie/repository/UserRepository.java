package com.stackroute.pie.repository;

import com.stackroute.pie.domain.Insured;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Insured,Long> {
    Optional<Insured> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    public boolean existsByInsuredId(int userId);
    public Optional<Insured> findByInsuredId(int userId);
    void deleteByUsername(String username);
}
