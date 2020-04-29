package com.scrapper.schemaScrapper.persistence.model;

import java.util.List;

public class CommonSchema extends AbstractSchema{
    private String name;

    public CommonSchema(List<SchemaKeyValues> keyValues, CommonSchema extendedCommonSchema, String name) {
        super(name, extendedCommonSchema, keyValues);
    }

//    @Override
//    public String getType() {
//        return name;
//    }

//    @Override
//    public String getId() {
//        return "" + this.hashCode();
//    }

}
