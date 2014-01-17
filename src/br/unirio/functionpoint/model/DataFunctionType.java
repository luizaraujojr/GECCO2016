package br.unirio.functionpoint.model;

import lombok.Getter;

/**
 * Types of data functions and their respective counts of function points
 * 
 * @author Marcio Barros
 */
public enum DataFunctionType 
{
	ILF("ILF", 7, 10, 15),
	EIF("EIF", 5, 7, 10);
	
	private @Getter String name;
	private @Getter int lowComplexityPoints;
	private @Getter int averageComplexityPoints;
	private @Getter int highComplexityPoints;
	
	private DataFunctionType(String name, int low, int avg, int high)
	{
		this.name = name;
		this.lowComplexityPoints = low;
		this.averageComplexityPoints = avg;
		this.highComplexityPoints = high;
	}

	public static DataFunctionType get(String type)
	{
		for (DataFunctionType dft : values())
			if (dft.getName().compareToIgnoreCase(type) == 0)
				return dft;
		
		return null;
	}
}