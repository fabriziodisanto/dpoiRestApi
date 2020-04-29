package com.scrapper.schemaScrapper.persistence.model;

import com.google.gson.annotations.SerializedName;

public class SchemaPublisher{
    @SerializedName(value = "@context")
    private String context;
    @SerializedName(value = "@type")
    private String type;
    private String url;
    private SchemaLogo logo;

    public SchemaPublisher(String url, SchemaLogo logo) {
        this.url = url;
        this.logo = logo;
    }

    public void setContextAndType(String context, String type) {
        this.context = context;
        this.type = type;
        this.logo.setContextAndType(context, "ImageObject");
    }

//    @Override
//    public String getType() {
//        return "Publisher";
//    }

//    @Override
//    public String getId() {
//        return "" + this.hashCode();
//    }
}
