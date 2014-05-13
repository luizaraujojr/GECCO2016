package br.unirio.overwork.model;

import lombok.Getter;
import br.unirio.simulation.Resource;

/**
 * Class that represents a developer
 * 
 * @author Marcio Barros
 */
public class Developer
{
	/**
	 * Developer's name
	 */
	private @Getter String name;
	
	/**
	 * Developer's productivity
	 */
	private @Getter double productivity;
	
	/**
	 * Developer's error generation rate for a unit of work
	 */
	private @Getter double errorGenerationRate;
	
	/**
	 * Developer's cost per hour
	 */
	private @Getter double hourlyCost;
	
	/**
	 * Effort available from the developer in a simulation step
	 */
	private @Getter Resource effort;

	/**
	 * Initializes the developer
	 */
	public Developer(String name, double hourlyCost)
	{
		super();
		this.name = name;
		this.hourlyCost = hourlyCost;
		this.productivity = 1.0;
		this.errorGenerationRate = 1.0;
		this.effort = new Resource("effort", this.productivity);
	}
}