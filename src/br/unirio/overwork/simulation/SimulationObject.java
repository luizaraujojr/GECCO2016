package br.unirio.overwork.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.simulation.state.IState;
import br.unirio.overwork.simulation.state.State;

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
	private @Getter @Setter double currentSimulationTime;
	
	/**
	 * Scenarios bound to the simulation object
	 */
	private List<Scenario> scenarios;
	
	/**
	 * States maintained by the simulation object
	 */
	private HashMap<String, IState> states;
	
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
		this.states = new HashMap<String, IState>();
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
	 * Returns the value of a state
	 */
	public double getState(String name, double _default)
	{
		if (states.containsKey(name))
			return states.get(name).get();
		
		states.put(name, new State(name, _default));
		return _default;
	}
	
	/**
	 * Sets the value of a variable handled by a scenario
	 */
	public void setState(String name, double value)
	{
		states.put(name, new State(name, value));
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
		this.startingTime = getCurrentSimulationTime();
	}

	/**
	 * Finishes the object's life-cycle
	 */
	public final void finish()
	{
		this.finished = true;
		this.finishingTime = getCurrentSimulationTime();
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