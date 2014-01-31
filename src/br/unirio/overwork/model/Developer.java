package br.unirio.overwork.model;

import lombok.Getter;
import br.unirio.overwork.simulation.StatefulObject;
import br.unirio.overwork.simulation.resource.Resource;

/**
 * Class that represents a developer
 * 
 * @author Marcio Barros
 */
public class Developer extends StatefulObject
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
	 * Effort available from the developer in a simulation step
	 */
	private @Getter Resource effort;

	/**
	 * Initializes the developer
	 */
	public Developer(String name)
	{
		super();
		this.name = name;
		this.productivity = 1.0;
		this.errorGenerationRate = 1.0;
		this.effort = new Resource("effort", this.productivity);
	}
}