package vttp.batch5.ssf.noticeboard.services;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;

@Service
public class NoticeService {
	
	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public ResponseEntity<String> postToNoticeServer(JsonObject payload, String url, String mapping) {
		RequestEntity<String> request = RequestEntity.post(url + mapping).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).body(payload.toString()); 

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.exchange(request, String.class);
	}
}
