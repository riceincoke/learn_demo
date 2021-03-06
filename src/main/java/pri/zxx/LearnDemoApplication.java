package pri.zxx;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author zxx
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
public class LearnDemoApplication {

    @Autowired
    private RedisTemplate redisTemplate;

// /   @Autowired
//    RedisUserMap redisUserMap;

    public static void main(String[] args) {
        SpringApplication.run(LearnDemoApplication.class, args);
    }

    @PostConstruct
    public void redisTemplate() {
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setKeySerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setHashKeySerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer(Object.class));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
