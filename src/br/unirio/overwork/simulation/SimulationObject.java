package br.unirio.overwork.simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents an abstract simulation object
 * 
 * @author Marcio Barros
 */
@SuppressWarnings("rawtypes")
public abstract class SimulationObject
{
	/**
	 * Simulation interval
	 */
	public static final double DT = 0.1;
	
	/**
	 * Object's name
	 */
	private @Getter String name;

	/**
	 * Indicates whether the object has started its life-cycle
	 */
	private boolean started;

	/**
	 * SImulation time when the object started its life-cycle
	 */
	private @Getter double startingTime;

	/**
	 * Indicates whether the object has finished its life-cycle
	 */
	private boolean finished;
	
	/**
	 * SImulation time when the object finished its life-cycle
	 */
	private @Getter double finishingTime;
	
	/**
	 * Current simulation time - set by the simulator
	 */
	private double currentSimulationTime;
	
	/**
	 * Current simulation blackboard - set by the simulator
	 */
	private StateBoard blackboard;
	
	/**
	 * States maintained by the simulation object
	 */
	private StateBoard localStates;
	
	/**
	 * Scenarios bound to the simulation object
	 */
	private List<Scenario> scenarios;
	
	/**
	 * Initializes the simulation object
	 */
	public SimulationObject(String name)
	{
		this.name = name;
		this.started = false;
		this.startingTime = -1.0;
		this.finished = false;
		this.finishingTime = -1.0;
		this.scenarios = new ArrayList<Scenario>();
		this.localStates = new StateBoard();
		this.blackboard = null;
	}
	
	/**
	 * Adds a scenario to the simulation object
	 */
	public void addScenario(Scenario scenario)
	{
		this.scenarios.add(scenario);
	}
	
	/**
	 * Returns the list of scenarios enacted upon the object
	 */
	public Iterable<Scenario> getScenarios()
	{
		return this.scenarios;
	}
	
	/**
	 * Returns the current simulation time to subclasses
	 */
	protected double getCurrentSimulationTime()
	{
		return currentSimulationTime;
	}
	
	/**
	 * Reads a local state as a double value
	 */
	public double getLocalState(String name, double _default)
	{
		return localStates.getState(name, _default);
	}
	
	/**
	 * Reads a local state as a boolean value
	 */
	public boolean getLocalState(String name, boolean _default)
	{
		return localStates.getState(name, _default);
	}

	/**
	 * Writes a local state as a double value
	 */
	public void setLocalState(String name, double value)
	{
		localStates.setState(name, value);
	}

	/**
	 * Writes a local state as a boolean value
	 */
	public void setLocalState(String name, boolean value)
	{
		localStates.setState(name, value);
	}
	
	/**
	 * Reads a global state as a double value
	 */
	public double getGlobalState(String name, double _default)
	{
		return blackboard.getState(name, _default);
	}
	
	/**
	 * Reads a global state as a boolean value
	 */
	public boolean getGlobalState(String name, boolean _default)
	{
		return blackboard.getState(name, _default);
	}

	/**
	 * Writes a global state as a double value
	 */
	public void setGlobalState(String name, double value)
	{
		blackboard.setState(name, value);
	}

	/**
	 * Writes a global state as a boolean value
	 */
	public void setGlobalState(String name, boolean value)
	{
		blackboard.setState(name, value);
	}

	/**
	 * Returns the objects that must be simulated before the current one
	 */
	public List<SimulationObject> getDependencies()
	{
		return null;
	}

	/**
	 * Prepares the object for a simulation step
	 */
	void prepare(double simulationTime, StateBoard blackboard)
	{
		this.currentSimulationTime = simulationTime;
		this.blackboard = blackboard;
	}
	
	/**
	 * Prepare the simulation for the object
	 */
	public void init()
	{
	}
	
	/**
	 * Indicates whether the object has started its life-cycle
	 */
	public boolean isStarted()
	{
		return started;
	}

	/**
	 * Indicates whether the object has finished its life-cycle
	 */
	public boolean isFinished()
	{
		return finished;
	}

	/**
	 * Starts the object's life-cycle
	 */
	public final void start()
	{
		beforeStart();
		this.started = true;
		this.startingTime = currentSimulationTime;
	}

	/**
	 * Finishes the object's life-cycle
	 */
	public final void finish()
	{
		this.finished = true;
		this.finishingTime = currentSimulationTime;
		afterFinish();
	}

	/**
	 * Method called before the object's simulation is started
	 */
	public void beforeStart()
	{
	}

	/**
	 * Method called before each step
	 */
	public void beforeStep()
	{	
	}

	/**
	 * Method called every simulation step
	 */
	public abstract boolean step();

	/**
	 * Method called after each step
	 */
	public void afterStep()
	{
	}

	/**
	 * Method called after the object's simulation is finished
	 */
	public void afterFinish()
	{
	}
}