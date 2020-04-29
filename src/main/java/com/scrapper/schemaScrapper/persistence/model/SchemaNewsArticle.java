package com.scrapper.schemaScrapper.persistence.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SchemaNewsArticle{
    @SerializedName(value = "@context")
    private String context;
    @SerializedName(value = "@type")
    private String type;
    private String headline;
    private String url;
    private String thumbnailUrl;
    private SchemaImage image;
    private Date dataCreated;
    private Date dataPublished;
    private Date dataModified;
    private String mainEntityOfPage;
    private String articleSection;
    private String[] author;
    private String[] creator;
    private SchemaPublisher publisher;

    public SchemaNewsArticle(String headline, String url, String thumbnailUrl,
                             SchemaImage image, Date dataCreated, Date dataPublished, Date dataModified,
                             String mainEntityOfPage, String articleSection, String[] author, String[] creator,
                             SchemaPublisher publisher) {
        this.headline = headline;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.image = image;
        this.dataCreated = dataCreated;
        this.dataPublished = dataPublished;
        this.dataModified = dataModified;
        this.mainEntityOfPage = mainEntityOfPage;
        this.articleSection = articleSection;
        this.author = author;
        this.creator = creator;
        this.publisher = publisher;
    }

    public void setContextAndType(String context, String type){
        this.context = context;
        this.type = type;
        this.image.setContextAndType(context, "ImageObject");
        this.publisher.setContextAndType(context, "Organization");
    }

//    @Override
//    public String getType() {
//        return "NewsArticle";
//    }

//    @Override
//    public String getId() {
//        return "" + this.hashCode();
//    }
}
