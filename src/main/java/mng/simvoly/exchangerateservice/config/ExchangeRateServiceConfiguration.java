package mng.simvoly.exchangerateservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration; 

/**
 * The service configuration parameters loaded from application.properties.
 * @author Michael
 *
 */
@Configuration
public class ExchangeRateServiceConfiguration{
	
	/**
	 * The data source host URL.
	 */
	@Value("${rates.source.url}")
	private String ratesSourceUrl;
	
	/**
	 * The supported currency pairs.
	 */
	@Value("${rates.supported}")
	private String[] supportedCurrencyPairs;
	
	/**
	 * Returns the currency exchange rate data source host URL.
	 * @return the source URL String.
	 */
	public String getRatesSourceUrl() {
		return ratesSourceUrl;
	}

	/**
	 * Returns the currency pairs supported by this service.
	 * @return a String[] containing the currency pairs.
	 */
	public String[] getSupportedCurrencyPairs() {
		return supportedCurrencyPairs;
	}
}
