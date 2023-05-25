package mng.simvoly.exchangerateservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mng.simvoly.exchangerateservice.bean.ExchangeRate;
import mng.simvoly.exchangerateservice.config.ExchangeRateServiceConfiguration;
import mng.simvoly.exchangerateservice.dao.ExchangeRateDAO;
import mng.simvoly.exchangerateservice.dao.ExchangeRateDAOException;

/**
 * The ExchangeRateService implementation.
 * @author Michael
 *
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	/**
	 * Time format used by this class.
	 */
	private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	/**
	 * DAO the data will be fetched from.
	 */
	@Autowired
	private ExchangeRateDAO ratesDAO;
	
	/**
	 * Service configuration properties.
	 */
	@Autowired
	private ExchangeRateServiceConfiguration configuration;
	
	@Override
	public ExchangeRate getRate(String currencyPair) throws ExchangeRateServiceException {
		if(! isSupportedCurrencyPair(currencyPair)){
			throw new IllegalArgumentException("unsupported currency pair "+currencyPair);
		}
		
		try {
			return new ExchangeRate(currencyPair,
									ratesDAO.getRate(currencyPair),
									LocalDateTime.now().format(DATE_TIME_FORMATTER));
		} catch (ExchangeRateDAOException e) {
			e.printStackTrace();
			//log error, send support notification 
			return null;   //the cache will ignore null values   
		}
	}

	@Override
	public String[] getSupportedCurrencyPairs() {
		return configuration.getSupportedCurrencyPairs();
	}

	@Override
	public boolean isSupportedCurrencyPair(String currencyPair) {
		for(String pair : configuration.getSupportedCurrencyPairs()) {
    		if(currencyPair.equalsIgnoreCase(pair))
    			return true;
    	}
    	
    	return false;
	}
}
