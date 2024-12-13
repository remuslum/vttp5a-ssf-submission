package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.components.JSONParser;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
import vttp.batch5.ssf.noticeboard.util.MyConstants;

@Service
public class NoticeService {
	@Autowired
	NoticeRepository noticeRepository;

	@Autowired
	JSONParser jsonParser;

	@Value("${rest.api.url}")
	private String restAPIURL;
	
	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public JsonObject postToNoticeServer(Notice notice) {
		JsonObject payload = jsonParser.parseNoticeToJSON(notice);
		String responseBody = sendRequestAndReceiveResponse(payload, restAPIURL, "/notice");
		JsonObject jsonObject = Json.createReader(new StringReader(responseBody)).readObject();
		return jsonObject;
	}

	private String sendRequestAndReceiveResponse(JsonObject payload, String url, String mapping){
		RequestEntity<String> request = RequestEntity.post(url + mapping).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).body(payload.toString()); 
		ResponseEntity<String> response;

		try {
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.exchange(request, String.class);
			noticeRepository.insertNotices(MyConstants.REDISKEY, response.getBody());
		} catch (HttpClientErrorException e){
			// Error from client request
			response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		} catch (HttpStatusCodeException e){
			// Error from server request
			JsonObject jsonObject = Json.createObjectBuilder().add("message", e.getMessage()).build();
			response = ResponseEntity.status(e.getStatusCode()).body(jsonObject.toString());
		}
		return response.getBody();
	}
}
