package br.unirio.functionpoint.model;

import lombok.Getter;

/**
 * Types of transaction functions
 * 
 * @author Marcio Barros
 */
public enum TransactionFunctionType 
{
	EI("EI", 3, 4, 6),
	EO("EO", 4, 5, 7),
	EQ("EQ", 3, 4, 6);
	
	private @Getter String name;
	private @Getter int lowComplexityPoints;
	private @Getter int averageComplexityPoints;
	private @Getter int highComplexityPoints;
	
	private TransactionFunctionType(String name, int low, int avg, int high)
	{
		this.name = name;
		this.lowComplexityPoints = low;
		this.averageComplexityPoints = avg;
		this.highComplexityPoints = high;
	}
	
	public static TransactionFunctionType get(String s)
	{
		for (TransactionFunctionType tft : values())
			if (s.compareToIgnoreCase(tft.getName()) == 0)
				return tft;
		
		return null;
	}
}