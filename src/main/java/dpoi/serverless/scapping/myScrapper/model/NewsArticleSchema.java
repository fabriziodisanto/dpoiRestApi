package dpoi.serverless.scapping.myScrapper.model;


import dpoi.serverless.scapping.myScrapper.PropertiesScrapper;

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