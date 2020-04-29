package com.scrapper.schemaScrapper.service;

import com.scrapper.schemaScrapper.persistence.model.AbstractSchema;

import java.util.List;

public interface SchemaService {
    List<String> getAllContainers(String container);

    AbstractSchema getThisResource(String container, String resource) throws IllegalArgumentException;

    AbstractSchema scrappThisUrl(String url);
}
