package com.scrapper.schemaScrapper.persistence.repository;

import com.scrapper.schemaScrapper.persistence.model.AbstractSchema;
import com.scrapper.schemaScrapper.persistence.model.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchemaRepository extends MongoRepository<AbstractSchema, String> {

    List<AbstractSchema> findAll();
}