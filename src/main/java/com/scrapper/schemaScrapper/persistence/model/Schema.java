package com.scrapper.schemaScrapper.persistence.model;

import java.util.List;

public interface Schema {
    String getType();
    String getId();
    AbstractSchema getPropertiesExtended();
    List<SchemaKeyValues> getKeyValues();
}
