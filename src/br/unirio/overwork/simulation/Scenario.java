package br.unirio.overwork.simulation;


public abstract class Scenario<T extends SimulationObject>
{
	public Scenario()
	{
	}

	public void connect(T t)
	{
		t.addScenario(this);
	}

	public void connect(Iterable<T> ts)
	{
		for (T t : ts)
			t.addScenario(this);
	}
	
	/**
	 * Binds the scenario to a simulation object
	 */
	public void init(T t)
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
	 * Executes the scenario before a live simulation step
	 */
	public void beforeLiveStep(T t) 
	{ 
	}

	/**
	 * Executes the scenario after a live simulation step
	 */
	public void afterLiveStep(T t) 
	{ 
	}
}