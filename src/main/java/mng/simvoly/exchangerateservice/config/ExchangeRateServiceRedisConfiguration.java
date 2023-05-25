package mng.simvoly.exchangerateservice.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory; 

/**
 * The Redis cache configuration parameters loaded from application.properties.
 * @author Michael
 *
 */
@Configuration
public class ExchangeRateServiceRedisConfiguration{
	
	/**
	 * Redis server host address.
	 */
	@Value("${spring.redis.host}")
	private String redisHost;
	
	/**
	 * Redis server host port.
	 */
	@Value("${spring.redis.port}")
	private int redisPort; 

	/**
	 * Cache item ttl/expiry.
	 */
	@Value("${spring.redis.ttl}")
	private long redisTtl; 
	
	/**
	 * Returns a LettuceConnectionFactory that provides connections to the specified Redis server.
	 * @return a configured LettuceConnectionFactory.
	 */
	@Bean 
	public LettuceConnectionFactory getConnectionFactory() {
		//System.out.println("Lettuce Config"); 
		return new LettuceConnectionFactory(
				new	RedisStandaloneConfiguration(redisHost, redisPort)); 
	}
	
	/**
	 * Returns a RedisCacheManager configured with the appropriate ttl and null-caching parameters.
	 * The ttl is set in application.properties.
	 * @param connectionFactory the connection factory to use.
	 * @return a configured RedisCacheManager.
	 */
	@Bean 
	public RedisCacheManager getRedisCacheManager(RedisConnectionFactory connectionFactory) { 
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(
				RedisCacheConfiguration
					.defaultCacheConfig() //
					.entryTtl(Duration.ofMillis(redisTtl))
					.disableCachingNullValues()).build(); 
	}
}