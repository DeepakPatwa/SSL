package com.stackroute.pie.repository;

import com.stackroute.pie.domain.FormFormat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends MongoRepository<FormFormat, Integer> {

    @Query
    public FormFormat findByFormName(String formName);

}
