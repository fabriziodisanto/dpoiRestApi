package com.scrapper.schemaScrapper.persistence.model;

import com.scrapper.schemaScrapper.PropertiesScrapper;

import java.util.List;

public class ProductSchema extends AbstractSchema {

    private ProductSchema(List<SchemaKeyValues> properties, CommonSchema commonSchema ) {
        super("Product", commonSchema, properties);
    }

    public ProductSchema(){
        this(PropertiesScrapper.scrapProperties("Product"), PropertiesScrapper.scrapExtendedProperty("Product"));
    }

//    @Override
//    public String getType() {
//        return "Product";
//    }

//    @Override
//    public String getId() {
//        return "" + this.hashCode();
//    }
}