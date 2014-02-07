package br.unirio.overwork.simulation;

/**
 * Abstract class that represents a scenario
 * 
 * @author Marcio
 */
public abstract class Scenario<T extends SimulationObject>
{
	/**
	 * Connects the scenario to a certain simulation object
	 */
	public void connect(T t)
	{
		t.addScenario(this);
	}

	/**
	 * Connects the scenario to a list of simulation objects
	 */
	public void connect(Iterable<T> ts)
	{
		for (T t : ts)
			t.addScenario(this);
	}
	
	/**
	 * Initializes the scenario under a simulation object
	 */
	public void init(T t)
	{
	}

	/**
	 * Executes the scenario before the start of an object's life-cycle
	 */
	public void beforeStart(T t) 
	{ 
	}

	/**
	 * Executes the scenario before a simulation step
	 */
	public void beforeStep(T t) 
	{ 
	}

	/**
	 * Executes the scenario after a simulation step
	 */
	public void afterStep(T t) 
	{ 
	}

	/**
	 * Executes the scenario after the end of an object's life-cycle
	 */
	public void afterFinish(T t) 
	{ 
	}
}