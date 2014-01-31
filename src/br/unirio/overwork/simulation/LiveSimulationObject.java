package br.unirio.overwork.simulation;

import java.util.List;

import lombok.Getter;

/**
 * Class that represents a simulation object that lives for a period of time
 * 
 * @author Marcio Barros
 */
public abstract class LiveSimulationObject extends SimulationObject
{
	/**
	 * Indicates whether the object has started its life-cycle
	 */
	private boolean started;

	/**
	 * SImulation time when the object started its life-cycle
	 */
	private @Getter double startingSimulationTime;

	/**
	 * Indicates whether the object has finished its life-cycle
	 */
	private boolean finished;
	
	/**
	 * SImulation time when the object finished its life-cycle
	 */
	private @Getter double finishingSimulationTime;
	
	/**
	 * Initializes the simulation object
	 */
	public LiveSimulationObject(String name)
	{
		super(name);
		this.started = false;
		this.startingSimulationTime = -1.0;
		this.finished = false;
		this.finishingSimulationTime = -1.0;
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
		return true;
	}
	
	/**
	 * Indicates whether the object has started its life-cycle
	 */
	@Override
	public boolean isStarted()
	{
		return started;
	}

	/**
	 * Indicates whether the object has finished its life-cycle
	 */
	@Override
	public boolean isFinished()
	{
		return finished;
	}

	/**
	 * Starts the object's life-cycle
	 */
	@Override
	public void start()
	{
		this.started = true;
		this.startingSimulationTime = getCurrentSimulationTime();
	}

	/**
	 * Finishes the object's life-cycle
	 */
	@Override
	public void finish()
	{
		this.finished = true;
		this.finishingSimulationTime = getCurrentSimulationTime();
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