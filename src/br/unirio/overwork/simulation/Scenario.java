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
	 * Performs a simulation step with the scenario
	 */
	public abstract void step(T t);
}