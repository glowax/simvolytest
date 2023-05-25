package mng.simvoly.exchangerateservice.dao;

/**
 * The ExchangeRateDAO interface.
 * @author Michael
 *
 */
public interface ExchangeRateDAO {

	/**
	 * Returns the exchange rate of the specified currency pair.
	 * @param currencyPair the currency pair rate to return.
	 * @return the rate as a double.
	 * @throws ExchangeRateDAOException if the DAO cannot retrieve the rate. 
	 */
	public double getRate(String currencyPair) throws ExchangeRateDAOException;
}
