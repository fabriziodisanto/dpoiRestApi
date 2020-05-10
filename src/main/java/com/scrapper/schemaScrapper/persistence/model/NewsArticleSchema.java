package com.scrapper.schemaScrapper.persistence.model;

import com.scrapper.schemaScrapper.PropertiesScrapper;

import java.util.List;

public class NewsArticleSchema extends AbstractSchema {

    private NewsArticleSchema(List<SchemaKeyValues> properties, CommonSchema commonSchema) {
        super("NewsArticle", commonSchema, properties);
    }

    public NewsArticleSchema(List<SchemaKeyValues> properties){
        this(properties, null);
    }

    public NewsArticleSchema() {
        this(PropertiesScrapper.scrapProperties("NewsArticle"), PropertiesScrapper.scrapExtendedProperty("NewsArticle"));
    }
}