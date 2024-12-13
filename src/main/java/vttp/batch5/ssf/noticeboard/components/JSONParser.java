package vttp.batch5.ssf.noticeboard.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;

@Component
public class JSONParser {
    @Autowired
    LocalDateConverter localDateConverter;

    public String parseNoticeToJSON(Notice notice){
        String dateInLong = localDateConverter.convertStringDateToLong(notice.getPostDate().toString());

        JsonObject jsonObject= Json.createObjectBuilder().add("title", notice.getTitle()).add("poster",notice.getPoster())
        .add("postDate", dateInLong).add("categories", notice.getCategories().toString())
        .add("text", notice.getText()).build();
        
        return jsonObject.toString();
    }
}
