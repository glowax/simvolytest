package mng.simvoly.exchangerateservice.service;

import mng.simvoly.exchangerateservice.bean.ExchangeRate;

/**
 * The ExchangeRatesService interface.
 * @author Michael
 *
 */
public interface ExchangeRateService {
	/**
	 * Returns the exchange rate of the specified currency pair.
	 * @param currencyPair the currency pair rate to return.
	 * @return a {@code ExchangeRate} object containing the pair, rate, and time.
	 * @throws ExchangeRateServiceException if the service is unable to provide the rate.
	 */
	public ExchangeRate getRate(String currencyPair) throws ExchangeRateServiceException;
	
	/**
	 * Returns the currency pairs supported by this service.
	 * @return a String[] containing the currency pairs.
	 */
	public String[] getSupportedCurrencyPairs();
	
	/**
	 * Checks if the specified currency pair is supported by this service.
	 * @param currencyPair the currency pair to check.
	 * @return true if the currency pair is supported.
	 */
	public boolean isSupportedCurrencyPair(String currencyPair);
}
