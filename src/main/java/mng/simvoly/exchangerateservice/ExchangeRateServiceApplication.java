package mng.simvoly.exchangerateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 * The Spring Boot application.
 * @author Michael
 *
 */
@SpringBootApplication
@EnableCaching
public class ExchangeRateServiceApplication {
	
	/**
	 * The application entry point. 
	 * @param args program arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateServiceApplication.class, args);
	}
}
