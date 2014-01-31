package br.unirio.overwork.simulation.resource;

import br.unirio.overwork.simulation.SimulationObject;

/**
 * Class that represents a resource used during a simulation
 * 
 * @author Marcio
 */
public class Resource implements IResource 
{
	/**
	 * Resource's name
	 */
	private String name;
	
	/**
	 * Amount of the resource available for every round
	 */
	private double amount;
	
	/**
	 * Amount of the resource available for the current round
	 */
	private double amountForRound;
	
	/**
	 * Initializes the resource
	 */
	public Resource(String name, double amount)
	{
		this.name = name;
		this.amount = amount;
		this.amountForRound = 0.0;
	}
	
	/**
	 * Returns the resource's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Resets the amount of resource available for the round
	 */
	public void reset()
	{
		amountForRound = amount * SimulationObject.DT;
	}

	/**
	 * Returns the amount of resource available for the round
	 */
	public double available()
	{
		return amountForRound;
	}

	/**
	 * Consumes an amount of resources
	 */
	public void consume(double amount)
	{
		amountForRound -= amount;
	}
}