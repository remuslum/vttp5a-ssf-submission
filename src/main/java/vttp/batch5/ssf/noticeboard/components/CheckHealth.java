package vttp.batch5.ssf.noticeboard.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CheckHealth {
    @Autowired
    @Qualifier("notice")
    private RedisTemplate<String, Object> redisTemplate;

    /* My command:
	 *	redis-cli -n 1 randomkey
     */ 
    public boolean isAppHealthy(){
        try {
            redisTemplate.randomKey();
            return true;
        } catch (Exception e){
            return false;
        } 
    }
}
