package br.unirio.overwork.simulation;

import java.util.List;

/**
 * Class that represents an abstract simulation object
 * 
 * @author Marcio Barros
 */
public abstract class BasicSimulationObject extends SimulationObject
{
	/**
	 * Initializes the simulation object
	 */
	public BasicSimulationObject(String name)
	{
		super(name);
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
	@Override
	public void init()
	{
	}
	
	/**
	 * Method called before a simulation step
	 */
	@Override
	public void beforeStep()
	{
	}
	
	/**
	 * Performs a simulation step
	 */
	@Override
	public void step()
	{
	}
	
	/**
	 * Indicates whether the object has a life-cycle
	 */
	@Override
	public boolean isLiveObject()
	{
		return false;
	}
	
	/**
	 * Indicates whether the object has started its life-cycle
	 */
	@Override
	public boolean isStarted()
	{
		return false;
	}

	/**
	 * Indicates whether the object has finished its life-cycle
	 */
	@Override
	public boolean isFinished()
	{
		return false;
	}

	/**
	 * Starts the object's life-cycle
	 */
	@Override
	public void start()
	{
	}

	/**
	 * Finishes the object's life-cycle
	 */
	@Override
	public void finish()
	{
	}

	/**
	 * Method called before the object's life-cycle is started
	 */
	@Override
	public void beforeStart()
	{
	}

	/**
	 * Method called before each live step
	 */
	@Override
	public void beforeLiveStep()
	{	
	}

	/**
	 * Method called every simulation step that the object's life-cycle is running
	 */
	public boolean liveStep()
	{
		return false;
	}

	/**
	 * Method called after each live step
	 */
	@Override
	public void afterLiveStep()
	{
	}

	/**
	 * Method called before the object's life-cycle is finished
	 */
	@Override
	public void beforeFinish()
	{
	}
}