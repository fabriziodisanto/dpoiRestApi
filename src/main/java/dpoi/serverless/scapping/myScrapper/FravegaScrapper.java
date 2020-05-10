package dpoi.serverless.scapping.myScrapper;

import dpoi.serverless.scapping.myScrapper.model.ProductSchema;
import dpoi.serverless.scapping.myScrapper.model.SchemaKeyValues;
import dpoi.serverless.scapping.myScrapper.model.ThingSchema;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FravegaScrapper {

    public static ProductSchema scrapp(String url){
        ProductSchema productSchema = new ProductSchema();
        ArrayList<SchemaKeyValues> schemaKeyValuesList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).timeout(10000).userAgent("Mozilla").get();

            SchemaKeyValues category = new SchemaKeyValues("category");
            Elements categoryDom = doc.select("[data-event=fvg.productDetail.click-breadcrumb]");
            for (int i = 1; i < categoryDom.size(); i++) {
                category.addValue(categoryDom.get(i).text());
            }
            schemaKeyValuesList.add(category);

            SchemaKeyValues productId = new SchemaKeyValues("productId");
            productId.addValue(doc.select(".KXQCQ").select("h2").text().split(" ")[1]);

//
//            Elements contentDetails = doc.select("div.Specifications__ContentDetails-i57k8a-4");
////            color
//            SchemaKeyValues color = new SchemaKeyValues("color");
//            color.addValue(contentDetails.get(11).text().split(" ")[1]);
//            schemaKeyValuesList.add(color);
////            depth
//            SchemaKeyValues depth = new SchemaKeyValues("depth");
//            depth.addValue(contentDetails.get(13).text().split(" ")[5]);
////            height
//            SchemaKeyValues height = new SchemaKeyValues("height");
//            height.addValue(contentDetails.get(13).text().split(" ")[1]);
////            width
//            SchemaKeyValues width = new SchemaKeyValues("width");
//            width.addValue(contentDetails.get(13).text().split(" ")[3]);
//
////            model
//            SchemaKeyValues model = new SchemaKeyValues("model");
//            model.addValue(contentDetails.get(15).text().split(" ")[1]);
//
////            weigth
//            SchemaKeyValues weight = new SchemaKeyValues("weight");
//            weight.addValue(contentDetails.get(14).text().split(" ")[1] + "g");
//

            ThingSchema thing = new ThingSchema();
            productSchema.setPropertiesExtended(thing);

            productSchema.addPropertyValue("description", doc.select(".DescriptionText__AltDescriptions-sc-14hdqca-1").text());
            productSchema.addPropertyValue("name", doc.select(".product-title").text());
            productSchema.addPropertyValue("url", url);

            for (SchemaKeyValues keyValues : schemaKeyValuesList) {
                for (String value : keyValues.getValues()) {
                    productSchema.addPropertyValue(keyValues.getName(), value);
                }
            }
        } catch (IOException e){
            return null;
        }
        return productSchema;
    }

    public static List<ProductSchema> scrappPages(List<String> urls){
        ArrayList<ProductSchema> result = new ArrayList<>();
        for (String url : urls) {
            ProductSchema productSchema = scrapp(url);
            if (productSchema != null) result.add(productSchema);
        }
        return result;
    }

}
