package com.scrapper.schemaScrapper;

import com.scrapper.schemaScrapper.persistence.model.CommonSchema;
import com.scrapper.schemaScrapper.persistence.model.SchemaKeyValues;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PropertiesScrapper {

    public static List<SchemaKeyValues> scrapProperties(String propertyName) {
        List<SchemaKeyValues> properties = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://schema.org/" + propertyName).timeout(10000).userAgent("Mozilla").get();
            Element table = doc.select("table.definition-table").first();
            boolean nextIsCorrect = false;
            for (Element child : table.children()) {
                if (nextIsCorrect) {
                    for (Element property : child.select("[property=rdfs:label]")) {
                        properties.add(new SchemaKeyValues(property.text()));
                    }
                    break;
                } else {
                    for (Element e : child.select("th.supertype-name")) {
                        String propertyTableName = e.getElementsByTag("a").text();
                        if (propertyTableName.equals(propertyName)) {
                            nextIsCorrect = true;
                        }
                    }
                }
            }
        } catch(HttpStatusException ignored){
        } catch(IOException e){
            e.printStackTrace();
        }
        return properties;
    }

    public static CommonSchema scrapExtendedProperty(String propertyName) {
        String nextProperty = "";
        try {
            Document doc = Jsoup.connect("https://schema.org/" + propertyName).timeout(10000).userAgent("Mozilla").get();
            Element table = doc.select("table.definition-table").first();
            boolean nextIsCorrect = false;
            for (Element child : table.children()) {
                if (nextIsCorrect) {
                    try {
                        nextProperty = child.select("th.supertype-name").get(0).getElementsByTag("a").text();
                    } catch (IndexOutOfBoundsException ignored){
                    }
                    break;
                } else {
                    for (Element e : child.select("th.supertype-name")) {
                        String propertyTableName = e.getElementsByTag("a").text();
                        if (propertyTableName.equals(propertyName)) {
                            nextIsCorrect = true;
                        }
                    }
                }
            }
        }catch(HttpStatusException ignored){
        } catch(IOException e){
            e.printStackTrace();
        }
        if (!nextProperty.isEmpty()) {
            return new CommonSchema(scrapProperties(nextProperty), scrapExtendedProperty(nextProperty), nextProperty);
        }
        return null;
    }
}
//     for (Element tbody : doc.select("tbody.supertype")) {
//                for (Element schema : tbody.select("th.supertype-name")) {
//                    String schemaValue = schema.getElementsByTag("a").text();
//                    if (!schemaValue.equals(propertyName)) {
//                        properties.add(new GoodImplementation.CommonSchema(scrapProperties(schemaValue)));
//                    }
//                }
//            }
