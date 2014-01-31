package br.unirio.overwork.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
	 * Current simulation time - set by the simulator
	 */
	private @Getter @Setter double currentSimulationTime;
	
	/**
	 * Scenarios bound to the simulation object
	 */
	private List<Scenario> scenarios;
	
	/**
	 * Variables used by scenarios imposed upon the simulation object
	 */
	private HashMap<String, Double> scenarioVariables;
	
	/**
	 * Initializes the simulation object
	 */
	public SimulationObject(String name)
	{
		this.name = name;
		this.scenarios = new ArrayList<Scenario>();
		this.scenarioVariables = new HashMap<String, Double>();
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
	 * Returns the value of a variable handled by a scenario
	 */
	public double getScenarioVariable(String name, double value)
	{
		if (scenarioVariables.containsKey(name))
			return scenarioVariables.get(name);
		
		scenarioVariables.put(name, value);
		return value;
	}
	
	/**
	 * Sets the value of a variable handled by a scenario
	 */
	public void setScenarioVariable(String name, double value)
	{
		scenarioVariables.put(name, value);
	}

	/**
	 * Returns the objects that must be simulated before the current one
	 */
	public abstract List<SimulationObject> getDependencies();
	
	/**
	 * Prepare the simulation for the object
	 */
	public abstract void init();
	
	/**
	 * Method called before a simulation step
	 */
	public abstract void beforeStep();
	
	/**
	 * Performs a simulation step
	 */
	public abstract void step();
	
	/**
	 * Indicates whether the object has a life-cycle
	 */
	public abstract boolean isLiveObject();
	
	/**
	 * Indicates whether the object has started its life-cycle
	 */
	public abstract boolean isStarted();

	/**
	 * Indicates whether the object has finished its life-cycle
	 */
	public abstract boolean isFinished();

	/**
	 * Starts the object's life-cycle
	 */
	public abstract void start();

	/**
	 * Finishes the object's life-cycle
	 */
	public abstract void finish();

	/**
	 * Method called before the object's life-cycle is started
	 */
	public abstract void beforeStart();

	/**
	 * Method called before each live step
	 */
	public abstract void beforeLiveStep();

	/**
	 * Method called every simulation step that the object's life-cycle is running
	 */
	public abstract boolean liveStep();

	/**
	 * Method called after each live step
	 */
	public abstract void afterLiveStep();

	/**
	 * Method called before the object's life-cycle is finished
	 */
	public abstract void beforeFinish();
}