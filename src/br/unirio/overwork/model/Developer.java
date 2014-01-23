package br.unirio.overwork.model;

import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.simulation.SimulationObject;

/**
 * Class that represents a developer
 * 
 * @author Marcio Barros
 */
public class Developer extends SimulationObject
{
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
	private @Getter @Setter double effortAvailable;

	/**
	 * Initializes the developer
	 */
	public Developer(String name)
	{
		super(name);
	}

	/**
	 * Prepares the simulation of the developer
	 */
	@Override
	public void init()
	{
		productivity = 1.0;
		errorGenerationRate = 1.0;
	}

	/**
	 * Simulation step for the developer
	 */
	@Override
	public void step()
	{
		effortAvailable = productivity * DT;
	}
}