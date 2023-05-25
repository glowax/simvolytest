package mng.simvoly.exchangerateservice.bean;

import java.io.Serializable;

/**
 * Exchange rate bean/record.
 * @author Michael
 *
 */
public record ExchangeRate(String pair, double rate, String time) implements Serializable{}
