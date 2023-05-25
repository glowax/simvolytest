package mng.simvoly.exchangerateservice.service;

/**
 * ExchangeRateServiceException.
 * @author Michael
 *
 */
public class ExchangeRateServiceException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ExchangeRateServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExchangeRateServiceException(String message) {
		super(message);
	}

	public ExchangeRateServiceException(Throwable cause) {
		super(cause);
	}
}
