package dpoi.serverless.scapping.myScrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dpoi.serverless.scapping.myScrapper.model.SchemaNewsArticle;
import dpoi.serverless.scapping.myScrapper.model.NewsArticleSchema;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LaNacionScrapper {

    public static NewsArticleSchema scrapp(String url){
        SchemaNewsArticle schemaNewsArticle = null;
        try {
            Document doc = Jsoup.connect(url).timeout(10000).userAgent("Mozilla").get();
            for (Element e : doc.select("#Schema_NewsArticle")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                schemaNewsArticle = gson.fromJson(e.data(), SchemaNewsArticle.class);
                }
        } catch (HttpStatusException ignored){
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (schemaNewsArticle != null) return schemaNewsArticle.toNewsArticleSchema();
        return null;
    }

    public static List<NewsArticleSchema> scrappPages(int start, int end){
        ArrayList<NewsArticleSchema> result = new ArrayList<>();
        String url = "http://www.lanacion.com.ar/";
        for (int i = start; i <= end; i++) {
            NewsArticleSchema schemaNewsArticle = scrapp(url + i);
            if (schemaNewsArticle != null) result.add(schemaNewsArticle);
        }
        return result;
    }

}
