package com.scrapper.schemaScrapper.presentation.controller;
import com.scrapper.schemaScrapper.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class SchemaResource {

    private SchemaService schemaService;

    @Autowired
    public SchemaResource(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @GetMapping("/{container}")
    public ResponseEntity getAllContainers(@PathVariable String container) {
        StringBuilder result = new StringBuilder();
        result.append("[")
                .append("\"@id\": \"http://localhost:8080/api/\" + container + \"/\",")
                .append("\"@type\": [")
                .append("\"http://www.w3.org/ns/ldp#BasicContainer\"")
                .append("\"http://purl.org/dc/terms/title\": [")
                .append("{\n").append("\"@value\": \"Container of \"").append(container).append(" resources\"")
                .append("\n}")
                .append("],")
                .append("\"http://www.w3.org/ns/ldp#contains\": [");
        schemaService.getAllContainers(container).forEach(schema -> {
            result.append("{")
                    .append("\"@id\": ").append("http://localhost:8080/api/").append(container).append("/").append(schema)
                    .append("},");
        });
        result.append("]}]");

        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    @GetMapping("/{container}/{resource}")
    public ResponseEntity getThisResource(@PathVariable String container, @PathVariable String resource) {
        try {
            return new ResponseEntity<>(schemaService.getThisResource(container, resource), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity("Id not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/startDatabase")
    public ResponseEntity scrapThisUrl() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://www.fravega.com/p/celular-libre-samsung-galaxy-a10s-azul-781862");
        urls.add("https://www.fravega.com/p/smart-tv-4k-50-philips-pug6513-77--502007");
        urls.add("https://www.fravega.com/p/secarropas-centrifugo-koh-i-noor-a-665-2-6-5-kg-280026");
        for (String url : urls) {
            schemaService.scrappThisUrl(url);
        }
        String url = "http://www.lanacion.com.ar/";
        for (int i = 2351900; i <= 2351905; i++) {
            schemaService.scrappThisUrl(url + i);
        }
        return new ResponseEntity<>("Database loaded with data", HttpStatus.OK);
    }
}
