package mng.simvoly.exchangerateservice;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mng.simvoly.exchangerateservice.config.ExchangeRateServiceConfiguration;
import mng.simvoly.exchangerateservice.config.ExchangeRateServiceRedisConfiguration;
import mng.simvoly.exchangerateservice.controller.ExchangeRateController;
import mng.simvoly.exchangerateservice.service.ExchangeRateService;

/**
 * Basic sanity test.
 * @author Michael
 *
 */
@SpringBootTest
class ExchangeRateServiceSanityTest {
	
	@Autowired
	ExchangeRateService ratesService;
	@Autowired
	ExchangeRateController controller;
	@Autowired
	ExchangeRateServiceConfiguration serviceConfig;
	@Autowired
	ExchangeRateServiceRedisConfiguration redisConfig;

	@Test
	void contextLoads() throws MalformedURLException {
		assert serviceConfig != null;
		assert serviceConfig.getSupportedCurrencyPairs() != null;
		assert serviceConfig.getSupportedCurrencyPairs().length > 0;
		new URL(serviceConfig.getRatesSourceUrl());							//throws MalformedURLException
		assert serviceConfig.getRatesSourceUrl().indexOf("{pair}") > 0;		//must contain the pair placeholder
		
		//the application wont start if the redis config is invalid, and we wont reach this point
		assert redisConfig.getConnectionFactory() != null;
		assert redisConfig.getConnectionFactory().getStandaloneConfiguration() != null;
		
		assert ratesService != null;
		assert ratesService.getSupportedCurrencyPairs().length > 0;
		
		assert controller != null;		
	}
}
