package mng.simvoly.exchangerateservice.dao;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Retrieves the rate data from the currency exchange rates data source URL.
 * @author Michael
 *
 */
@Repository
public class ExchangeRateSourceDAO implements ExchangeRateDAO{
	
	/**
	 * Source API URL.
	 */
	@Value("${rates.source.url}")
	private String sourceUrl;
	
	/**
	 * ObjectMapper to parse JSON responses from the source URL.
	 */
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public double getRate(String currencyPair) throws ExchangeRateDAOException {
		//return BigDecimal.valueOf(0.7+(0.85-0.7)*new Random().nextDouble()).setScale(6, RoundingMode.HALF_UP).doubleValue();
		try {
			return (double) objectMapper.readTree(
					new URL(sourceUrl.replaceFirst("\\{pair\\}", currencyPair)))
					.get(currencyPair.split("/")[1])
					.doubleValue();
		} catch (IOException e) {
			throw new ExchangeRateDAOException("unable to get exchange rate data from rates API", e);
		}
	}
}