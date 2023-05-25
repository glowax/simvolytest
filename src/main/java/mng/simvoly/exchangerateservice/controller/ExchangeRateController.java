package mng.simvoly.exchangerateservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mng.simvoly.exchangerateservice.bean.ExchangeRate;
import mng.simvoly.exchangerateservice.service.ExchangeRateService;
import mng.simvoly.exchangerateservice.service.ExchangeRateServiceException;

/**
 * The ExchangeRateController exposes 2 API end points:<br>
 * {@code /exchange-rates/rate?pair=abx/xyz}, which returns the exchange rate of the specified currency pair, and<br>
 * {@code /exchange-rates/supported}, which returns all the currency pairs supported by this service.
 * 
 * @author Michael
 *
 */
@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {
	
	@Autowired
	private ExchangeRateService rateService;
	
	/**
	  * Returns the exchange rate for the specified currency pair.
	  * @param currencyPair the currency pair rate to return.
	  * @return a {@code ExchangeRate} object containing the currency pair, rate, and time.
	  */
	 @GetMapping("/{rate}")
	 @Cacheable("rate")
	 public ExchangeRate getCurrencyRate(@RequestParam(value="pair", required=true) String currencyPair) {
		 if (! rateService.isSupportedCurrencyPair(currencyPair)) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
    				"Unsupported currency pair "+currencyPair+
    				". Supported currency pairs: "+String.join(", ", rateService.getSupportedCurrencyPairs()));
        }

		try {
			return rateService.getRate(currencyPair);
		} catch (ExchangeRateServiceException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Unable to get exchange rate");
		}
    }
	 
	/**
	 * Returns the supported currency pairs. May be useful for front-end or 3rd party API developers.
	 * @return the currency pairs as a JSON string.
	 */
	 @GetMapping("/supported")
	 @Cacheable("supported")
	 public String[] getSupportedCurrencyPairs() {
		 return rateService.getSupportedCurrencyPairs();
	 }
}
