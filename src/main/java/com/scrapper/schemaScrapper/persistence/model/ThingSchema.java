package com.scrapper.schemaScrapper.persistence.model;

import com.scrapper.schemaScrapper.PropertiesScrapper;

import java.util.List;

public class ThingSchema extends AbstractSchema {

    private ThingSchema(List<SchemaKeyValues> properties, CommonSchema commonSchema ) {
        super("Thing", commonSchema, properties);
    }

    public ThingSchema(){
        this(PropertiesScrapper.scrapProperties("Thing"), PropertiesScrapper.scrapExtendedProperty("Thing"));
    }

//    @Override
//    public String getType() {
//        return "Thing";
//    }

//    @Override
//    public String getId() {
//        return "" + this.hashCode();
//    }

}
