package mng.simvoly.exchangerateservice;

import java.time.format.DateTimeFormatter;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mng.simvoly.exchangerateservice.bean.ExchangeRate;
import mng.simvoly.exchangerateservice.config.ExchangeRateServiceConfiguration;

/**
 * Tests the data returned by the web interface.
 * @author Michael
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ExchangeRateServiceIntegrationTest {
	
	@Value(value="${local.server.port}")
	int port=8080;
	@Autowired
	TestRestTemplate restTemplate;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ExchangeRateServiceConfiguration configuration;
	
	@Test
	void contextLoads() throws JsonMappingException, JsonProcessingException {

		//check the supported currency pairs returned match the config
		var config = Arrays.asList(configuration.getSupportedCurrencyPairs());
		var returned = Arrays.asList(
				(String[])objectMapper.readValue(
						restTemplate.getForObject("http://localhost:"+port+"/exchange-rates/supported", String.class), String[].class));
		
		assert returned.size() == config.size();
		assert config.containsAll(returned);
		
		ExchangeRate rate;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		//check the service returns valid data for each pair
		for(String pair : configuration.getSupportedCurrencyPairs()) {
			rate = (ExchangeRate)objectMapper.readValue(
						restTemplate.getForObject("http://localhost:"+port+"/exchange-rates/rate?pair="+pair, String.class), ExchangeRate.class);
			assert rate.pair().equals(pair);
			assert rate.rate() > 0;
			dateFormatter.parse(rate.time()); 			//will throw a DateTimeParseException if invalid
		}
		
		//TODO test negative response 
	}
}
