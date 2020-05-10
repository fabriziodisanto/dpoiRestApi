package dpoi.serverless.scapping.myScrapper.model;

import com.google.gson.annotations.SerializedName;

public class SchemaImage{
    @SerializedName(value = "@context")
    private String context;
    @SerializedName(value = "@type")
    private String type;
    private String url;
    private int height;
    private int width;

    public SchemaImage(String url, int height, int width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public void setContextAndType(String context, String type) {
        this.context = context;
        this.type = type;
    }

//    @Override
//    public String getType() {
//        return "SchemaImage";
//    }

//    @Override
//    public String getId() {
//        return "" + this.hashCode();
//    }
}
