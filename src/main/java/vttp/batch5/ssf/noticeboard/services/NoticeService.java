package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
import vttp.batch5.ssf.noticeboard.util.MyConstants;

@Service
public class NoticeService {
	@Autowired
	NoticeRepository noticeRepository;
	
	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public String postToNoticeServer(JsonObject payload, String url, String mapping) {
		RequestEntity<String> request = RequestEntity.post(url + mapping).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).body(payload.toString()); 
		ResponseEntity<String> response;

		try {
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.exchange(request, String.class);
			noticeRepository.insertNotices(MyConstants.REDISKEY, response.getBody());
		} catch (HttpClientErrorException e){
			response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
		return response.getBody();
	}
}
