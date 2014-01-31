package br.unirio.overwork.simulation.resource;

/**
 * Interface that represents a resource used during a simulation
 * 
 * @author Marcio
 */
public interface IResource 
{
	/**
	 * Returns the resource's name
	 */
	public String getName();
	
	/**
	 * Resets the amount of resource available for the round
	 */
	public void reset();
	
	/**
	 * Returns the amount of resource available for the round
	 */
	public double available();
	
	/**
	 * Consumes an amount of resources
	 */
	public void consume(double amount);
}