package br.unirio.overwork.simulation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents an abstract simulation object
 * 
 * @author Marcio Barros
 */
public abstract class SimulationObject
{
	/**
	 * Simulation interval
	 */
	protected static final double DT = 0.1;
	
	/**
	 * Object's name
	 */
	private @Getter String name;
	
	/**
	 * Current simulation time - set by the simulator
	 */
	private @Getter @Setter double currentSimulationTime;
	
	/**
	 * Initializes the simulation object
	 */
	public SimulationObject(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the objects that must be simulated before the current one
	 */
	public List<SimulationObject> getDependencies()
	{
		return null;
	}
	
	/**
	 * Prepare the simulation for the object
	 */
	public abstract void init();
	
	/**
	 * Perform a simulation step
	 */
	public abstract void step();
}