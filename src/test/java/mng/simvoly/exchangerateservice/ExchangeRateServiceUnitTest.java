package mng.simvoly.exchangerateservice;

import java.time.format.DateTimeFormatter;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import mng.simvoly.exchangerateservice.bean.ExchangeRate;
import mng.simvoly.exchangerateservice.config.ExchangeRateServiceConfiguration;
import mng.simvoly.exchangerateservice.service.ExchangeRateService;
import mng.simvoly.exchangerateservice.service.ExchangeRateServiceException;

/**
 * ExchangeRatesService unit test. 
 * @author Michael
 *
 */
@SpringBootTest
class ExchangeRateServiceUnitTest {
	
	@Autowired
	ExchangeRateServiceConfiguration configuration;
	@Autowired
	ExchangeRateService ratesService;
	
	@Test
	void contextLoads() throws JsonMappingException, JsonProcessingException, ExchangeRateServiceException {
		
		//check the supported currency pairs returned match the config
		var config = Arrays.asList(configuration.getSupportedCurrencyPairs());
		var returned = Arrays.asList(ratesService.getSupportedCurrencyPairs());

		assert returned.size() == config.size();
		assert config.containsAll(returned);
		
		ExchangeRate rate;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		//check the service returns valid data for each pair
		for(var pair : ratesService.getSupportedCurrencyPairs()) {
			assert ratesService.isSupportedCurrencyPair(pair);
			rate = ratesService.getRate(pair);
			assert rate.pair().equals(pair);
			assert rate.rate() > 0;
			dateFormatter.parse(rate.time());			//will throw a DateTimeParseException if invalid
		}
		
		//check the service rejects invalid pairs
		assert ! ratesService.isSupportedCurrencyPair("null/null"); 
	}
}
