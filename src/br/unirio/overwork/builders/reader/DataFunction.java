package br.unirio.overwork.builders.reader;

import lombok.Getter;

/**
 * Class that represents a data function 
 * 
 * @author Marcio Barros
 */
class DataFunction
{
	/**
	 * Transaction name
	 */
	private @Getter String name;

	/**
	 * Number of function points
	 */
	private @Getter int functionPoints;

	/**
	 * Initializes a data function
	 */
	public DataFunction(String name, int functionPoints)
	{
		this.name = name;
		this.functionPoints = functionPoints;
	}
}