package vttp.batch5.ssf.noticeboard.components;

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
        // Chnange LocalDate to Long
        Long dateInLong = localDateConverter.convertStringDateToLong(notice.getPostDate().toString());

        // Store categories in JSONArray
        JsonArrayBuilder categoriesArrayBuilder = Json.createArrayBuilder();
        notice.getCategories().forEach(x -> categoriesArrayBuilder.add(x));
        JsonArray categoriesArray = categoriesArrayBuilder.build();

        JsonObject jsonObject= Json.createObjectBuilder().add("title", notice.getTitle()).add("poster",notice.getPoster())
        .add("postDate", dateInLong).add("categories", categoriesArray)
        .add("text", notice.getText()).build();

        return jsonObject;
    }
}
