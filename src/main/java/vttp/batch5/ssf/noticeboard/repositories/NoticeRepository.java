package vttp.batch5.ssf.noticeboard.repositories;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Repository
public class NoticeRepository {

	@Autowired
	@Qualifier("notice")
	private RedisTemplate<String, Object> redisTemplate;

	HashOperations<String, String, String> hashOperations;

	@PostConstruct
	public void init(){
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		hashOperations = redisTemplate.opsForHash();
	}
	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 * My command:
	 *	redis-cli -n 1 hmset notice 01JEZ8BXJHV6W21CEGPC7FEA7M {"id":"01JEZ8BXJHV6W21CEGPC7FEA7M","timestamp":1734068270673}
	 */
	public void insertNotices(String redisKey, String responseBody) {
		// Save responseBody as a JSON String
		JsonObject jsonObject = Json.createReader(new StringReader(responseBody)).readObject();
		hashOperations.put(redisKey, jsonObject.getString("id"), jsonObject.toString());
	}


}
