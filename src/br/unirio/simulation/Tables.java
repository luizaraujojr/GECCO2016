package br.unirio.simulation;

/**
 * Table management functions
 * 
 * @author Marcio Barros
 */
public class Tables
{
	public static double lookup(double[] table, double value, double minValue, double maxValue)
	{
		int len = table.length;
		
		if (value <= minValue)
			return table[0];
		
		if (value >= maxValue)
			return table[len-1];
		
		int index = (int) (len * (value - minValue) / (maxValue - minValue));
		return table[index];
	}
}