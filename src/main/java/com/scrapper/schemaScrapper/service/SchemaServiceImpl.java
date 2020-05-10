package com.scrapper.schemaScrapper.service;

import com.scrapper.schemaScrapper.FravegaScrapper;
import com.scrapper.schemaScrapper.LaNacionScrapper;
import com.scrapper.schemaScrapper.persistence.model.*;
import com.scrapper.schemaScrapper.persistence.repository.SchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchemaServiceImpl implements SchemaService {

    private SchemaRepository schemaRepository;

    @Autowired
    public SchemaServiceImpl(SchemaRepository schemaRepository) {
        this.schemaRepository = schemaRepository;
    }

    @Override
    public List<String> getAllContainers(String container) {
        ArrayList<String> result = new ArrayList();
        schemaRepository.findAllByName(container).forEach(schema -> {
            result.add(schema.getId());
        });
        return result;
    }

    @Override
    public AbstractSchema getThisResource(String container, String resource) throws IllegalArgumentException{
        for (AbstractSchema schema : schemaRepository.findAll()) {
            if (schema.getType().equals(container) && schema.getId().equals(resource)) {
                return schema;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public AbstractSchema scrappThisUrl(String url) {
        if(url.contains("www.fravega.com")){
            ProductSchema schema = FravegaScrapper.scrapp(url);
            schemaRepository.insert(schema);
            return schema;
        }
        else if(url.contains("www.lanacion.com")){
            NewsArticleSchema schema = LaNacionScrapper.scrapp(url);
            if (schema != null) {
                schemaRepository.insert(schema);
                return schema;
            }
        }
        return null;
    }
}
