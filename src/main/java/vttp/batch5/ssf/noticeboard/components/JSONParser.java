package vttp.batch5.ssf.noticeboard.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;

@Component
public class JSONParser {
    @Autowired
    LocalDateConverter localDateConverter;

    public JsonObject parseNoticeToJSON(Notice notice){
        // Change LocalDate to Long
        Long dateInLong = localDateConverter.convertStringDateToLong(notice.getPostDate().toString());

        // Change categories to JsonArray
        JsonArray categoriesArray = convertCategoriesToJSON(notice.getCategories());

        JsonObject jsonObject= Json.createObjectBuilder().add("title", notice.getTitle()).add("poster",notice.getPoster())
        .add("postDate",dateInLong).add("categories", categoriesArray)
        .add("text", notice.getText()).build();

        return jsonObject;
    }

    private JsonArray convertCategoriesToJSON(List<String> categories){
        JsonArrayBuilder categoriesArrayBuilder = Json.createArrayBuilder();
        categories.forEach(x -> categoriesArrayBuilder.add(x));
        return categoriesArrayBuilder.build();
    }
}
