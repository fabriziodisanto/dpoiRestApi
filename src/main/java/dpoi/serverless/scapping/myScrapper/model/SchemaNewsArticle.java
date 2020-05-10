package dpoi.serverless.scapping.myScrapper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//    public void setContextAndType(String context, String type){
//        this.context = context;
//        this.type = type;
//        this.image.setContextAndType(context, "ImageObject");
//        this.publisher.setContextAndType(context, "Organization");
//    }

    public NewsArticleSchema toNewsArticleSchema() {
        return new NewsArticleSchema(getAllKeyValues());
    }

    private List<SchemaKeyValues> getAllKeyValues() {
        ArrayList<SchemaKeyValues> schemaKeyValues = new ArrayList<>();
        schemaKeyValues.add(new SchemaKeyValues("headline").addValue(this.headline));
        schemaKeyValues.add(new SchemaKeyValues("url").addValue(this.url));
        schemaKeyValues.add(new SchemaKeyValues("thumbnailUrl").addValue(this.thumbnailUrl));
        try {
            schemaKeyValues.add(new SchemaKeyValues("dataCreated").addValue(this.dataCreated.toString()));
        } catch (NullPointerException ignored){

        }
        try {
            schemaKeyValues.add(new SchemaKeyValues("dataPublished").addValue(this.dataPublished.toString()));
        } catch (NullPointerException ignored){

        }
        try{
            schemaKeyValues.add(new SchemaKeyValues("dataModified").addValue(this.dataModified.toString()));
        } catch (NullPointerException ignored){

        }
        schemaKeyValues.add(new SchemaKeyValues("mainEntityOfPage").addValue(this.mainEntityOfPage));
        schemaKeyValues.add(new SchemaKeyValues("articleSection").addValue(this.articleSection));
        for (String author : this.author) {
            schemaKeyValues.add(new SchemaKeyValues("author").addValue(author));
        }
        for (String creator : this.creator) {
            schemaKeyValues.add(new SchemaKeyValues("creator").addValue(creator));
        }
        return schemaKeyValues;
    }
}
